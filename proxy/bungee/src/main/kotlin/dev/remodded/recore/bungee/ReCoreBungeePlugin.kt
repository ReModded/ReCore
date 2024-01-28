package dev.remodded.recore.bungee

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.ReCorePlugin
import dev.remodded.recore.api.config.ConfigManager
import dev.remodded.recore.api.config.DatabaseType
import dev.remodded.recore.api.database.DatabaseProvider
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.Constants
import dev.remodded.recore.common.config.DefaultConfigManager
import dev.remodded.recore.common.config.ReCoreConfig
import dev.remodded.recore.common.database.MariaDBProvider
import dev.remodded.recore.common.database.MySQLProvider
import dev.remodded.recore.common.database.PostgreSQLProvider
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path

class ReCoreBungeePlugin(
    private val plugin: Plugin,
    private val libraryLoader: LibraryLoader
) : ReCorePlugin {

    private var logger: Logger = LoggerFactory.getLogger("ReCore")
    private lateinit var databaseProvider: DatabaseProvider
    private lateinit var config: ReCoreConfig

    init {
        loadConfig()
        initDatabase()
        ReCore.INSTANCE = this
    }

    override fun getLibraryLoader(): LibraryLoader {
        return libraryLoader
    }

    override fun getPlatformInfo() = PlatformInfo(
        Platform.BUNGEE,
        ProxyServer.getInstance().name,
        ProxyServer.getInstance().version,
        "proxy"
    )

    override fun <T> getConfigLoader(pluginName: String, configClass: Class<T>): ConfigManager<T> {
        val path = Path.of("./config")
        return DefaultConfigManager(path, pluginName, configClass)
    }

    override fun getDatabaseProvider(): DatabaseProvider {
        return databaseProvider
    }

    private fun loadConfig() {
        try {
            logger.info("Loading configuration")
            val cfg = getConfigLoader(Constants.NAME, ReCoreConfig::class.java).getConfig("ReCore.conf")
            if (cfg == null) {
                logger.error("Config file ReCore.conf not loaded properly")
                return
            }
            logger.info("Config ReCore.conf loaded successfully")
            config = cfg
        } catch (e: Exception) {
            logger.error("Error while loading ReCore.conf", e)
        }
    }

    private fun initDatabase() {
        val dbConfig = config.database
        when(dbConfig.databaseType) {
            DatabaseType.POSTGRESQL -> this.databaseProvider = PostgreSQLProvider(dbConfig)
            DatabaseType.MYSQL -> this.databaseProvider = MySQLProvider(dbConfig)
            DatabaseType.MARIADB -> this.databaseProvider = MariaDBProvider(dbConfig)
        }
    }
}
