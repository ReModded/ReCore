package dev.remodded.recore.common.database.migrations

import dev.remodded.recore.api.database.DatabaseProvider
import dev.remodded.recore.api.database.use
import dev.remodded.recore.api.plugins.ReCorePlugin
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.InputStream
import java.sql.SQLException
import java.time.Duration
import java.time.Instant
import java.util.function.Supplier
import java.util.zip.ZipInputStream

class DatabaseMigrator(
    val plugin: ReCorePlugin,
    val database: DatabaseProvider,
) {
    companion object {
        private const val DEFAULT_MIGRATION_FOLDER = "db/migrations"
        private const val LOG_PREFIX = "[DatabaseMigrator]"

        private val TIMESTAMP_PATTERN = Regex("""--[ \t]*(\d)+""")

        private var isSetup = false
        fun setup(database: DatabaseProvider) {
            if (isSetup) return

            try {
                database.getDataSource().connection.use {
                    prepareStatement("CREATE TABLE IF NOT EXISTS migrations (plugin text PRIMARY KEY, lastMigrationTimestamp bigint)").execute()
                }
            } catch (e: SQLException) {
                throw DatabaseMigrationException("Failed to create migrations table", e)
            }

            isSetup = true
        }
    }

    private val logger = plugin.logger ?: LoggerFactory.getLogger(plugin.getPluginInfo().name + " Tmp")
    private val migrations = mutableListOf<DatabaseMigration>()

    init {
        setup(database)

        loadMigrations(DEFAULT_MIGRATION_FOLDER)
    }

    fun migrate() {
        val lastMigration = getLastMigration()

        val startTime = Instant.now()

        val requiredMigrations = migrations.filter { lastMigration == null || it.timestamp > lastMigration }.sortedBy { it.timestamp }

        if (requiredMigrations.isEmpty()) {
            logger.info("$LOG_PREFIX No migrations to apply")
            return
        }

        logger.info("$LOG_PREFIX Applying ${requiredMigrations.size} migrations")

        var newLastMigration: Long? = null

        database.getDataSource().connection.use {
            createStatement().use {
                requiredMigrations.forEach { migration ->
                    val inputStream = migration.inputStreamProvider.get()
                    try {
                        inputStream.use {
                            logger.info("$LOG_PREFIX Applying migration ${migration.filename}")
                            execute(inputStream.reader().readText())
                        }
                        newLastMigration = migration.timestamp
                    } catch (e: SQLException) {
                        if (newLastMigration != null)
                            setLastMigration(newLastMigration!!)

                        throw DatabaseMigrationException("Failed to apply migration ${migration.filename}", e)
                    }
                }
            }
        }

        if (newLastMigration != null)
            setLastMigration(newLastMigration!!)

        val delta = Duration.between(startTime, Instant.now()).toMillis() / 1000.0f
        logger.info("$LOG_PREFIX Applying migrations took $delta seconds")
    }

    fun loadMigrations(folder: String) {
        val files = arrayListOf<String>()
        val startTime = Instant.now()

        val mainClass = plugin.javaClass

        val src = mainClass.getProtectionDomain().codeSource
        if (src != null) {
            try {
                val jar = src.location
                val zip = ZipInputStream(jar.openStream())
                while (true) {
                    val e = zip.nextEntry ?: break
                    val name = e.name
                    if (name.startsWith("$folder/")) {
                        if (name == "$folder/") {
                            continue
                        }
                        files.add(name)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (files.size == 0) {
            logger.warn("$LOG_PREFIX No migrations were added while searching the directory: $folder")
            return
        }

        for (fileName in files) {
            addMigration(fileName.substring(folder.length + 1)) {
                mainClass.getClassLoader().getResourceAsStream(fileName)!!
            }
        }

        val delta = Duration.between(startTime, Instant.now()).toMillis()
        logger.debug("$LOG_PREFIX Locating the migrations took ${delta / 1000.0f} seconds")
    }

    fun addMigration(filename: String, inputStreamProvider: Supplier<InputStream>) {
        val timestamp = inputStreamProvider.get().use {
            if (available() == 0) {
                logger.warn("$LOG_PREFIX Migration file $filename is empty. Skipping.")
                return
            }

            val firstLine = bufferedReader().readLine()
            if (firstLine == null) {
                logger.warn("$LOG_PREFIX Migration file $filename is empty. Skipping.")
                return
            }

            val result = TIMESTAMP_PATTERN.matchEntire(firstLine)

            if (result == null || result.groupValues.size < 2) {
                logger.warn("$LOG_PREFIX Migration file $filename does not start with a timestamp. Skipping.")
                return
            }

            result.groupValues[1].toLong()
        }
        migrations.add(DatabaseMigration(filename, timestamp, inputStreamProvider))
    }

    private fun setLastMigration(timestamp: Long) {
        database.getDataSource().connection.use {
            prepareStatement("INSERT INTO migrations (plugin, lastMigrationTimestamp) VALUES (?, ?) ON CONFLICT (plugin) DO UPDATE SET lastMigrationTimestamp=?").use {
                setString(1, plugin.getPluginInfo().id)
                setLong(2, timestamp)
                setLong(3, timestamp)
                execute()
            }
        }
    }

    private fun getLastMigration(): Long? {
        // Get the last migration from the database
        return database.getDataSource().connection.use {
            prepareStatement("SELECT lastMigrationTimestamp FROM migrations WHERE plugin=?").use {
                setString(1, plugin.getPluginInfo().id)
                executeQuery().use {
                    if (next()) {
                        getLong(1)
                    } else {
                        null
                    }
                }
            }
        }
    }
}
