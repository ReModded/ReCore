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

        private val TIMESTAMP_PATTERN = Regex("""--(?![ \t])*(\d)+""")

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

        database.getDataSource().connection.use {
            createStatement().use {
                requiredMigrations.forEach { migration ->
                    val inputStream = migration.inputStreamProvider.get()
                    try {
                        inputStream.use {
                            logger.info("$LOG_PREFIX Applying migration ${migration.filename}")
                            execute(inputStream.reader().readText())
                        }
                    } catch (e: SQLException) {
                        throw DatabaseMigrationException("Failed to apply migration ${migration.filename}", e)
                    }
                    executeUpdate("INSERT INTO migrations (plugin, lastMigrationTimestamp) VALUES ('${plugin.getPluginInfo().id}', '${lastMigration}') ON CONFLICT (plugin) DO UPDATE SET lastMigrationTimestamp='${lastMigration}'")
                }
            }
        }

        val delta = Duration.between(Instant.now(), startTime).toMillis() / 1000.0f
        logger.info("$LOG_PREFIX Applying migrations took $delta seconds")
    }

    fun loadMigrations(folder: String) {
        val files: MutableList<String> = ArrayList()
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

        val delta = Duration.between(Instant.now(), startTime).toMillis()
        logger.info("$LOG_PREFIX Locating the migrations took ${delta / 1000.0f} seconds")
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

    private fun getLastMigration(): Long? {
        // Get the last migration from the database
        return database.getDataSource().connection.use {
            createStatement().use {
                executeQuery("SELECT timestamp FROM migrations WHERE plugin='${plugin.getPluginInfo().id}'").use {
                    if (next()) {
                        getLong("timestamp")
                    } else {
                        null
                    }
                }
            }
        }
    }
}
