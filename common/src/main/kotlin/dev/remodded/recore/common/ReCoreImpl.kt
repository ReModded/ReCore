package dev.remodded.recore.common

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.ReCoreAPI
import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.config.DatabaseType
import dev.remodded.recore.api.database.DatabaseProvider
import dev.remodded.recore.common.config.DefaultConfigManager
import dev.remodded.recore.common.config.ReCoreConfig
import dev.remodded.recore.common.database.MariaDBProvider
import dev.remodded.recore.common.database.MySQLProvider
import dev.remodded.recore.common.database.PostgreSQLProvider
import org.slf4j.LoggerFactory

class ReCoreImpl (
    private val platform: ReCorePlatform
) : ReCore {

    private val config: ReCoreConfig = loadConfig()
    private val databaseProvider: DatabaseProvider = initDatabase()


    override fun getPlatform() = platform

    override fun getDatabaseProvider() = databaseProvider

    override fun <T> getConfigLoader(pluginName: String, configClass: Class<T>) = 
        DefaultConfigManager(platform.getPlatformInfo().dataFolder.resolve(pluginName), configClass)


    companion object {
        private val logger = LoggerFactory.getLogger("ReCore")

        fun init(platform: ReCorePlatform) {
            logger.info("Initializing")
            ReCoreAPI.INSTANCE = ReCoreImpl(platform)
        }
    }

    private fun loadConfig(): ReCoreConfig {
        try {
            logger.info("Loading configuration")
            val cfg = getConfigLoader(Constants.NAME, ReCoreConfig::class.java).getConfig("ReCore.conf")!!
            logger.info("Config ReCore.conf loaded successfully")
            return cfg
        } catch (e: Exception) {
            logger.error("Error while loading ReCore.conf", e)
            logger.info("Disabling plugin")

            return ReCoreConfig()
        }
    }

    private fun initDatabase(): DatabaseProvider {
        val dbConfig = config.database
        return when(dbConfig.databaseType) {
            DatabaseType.POSTGRESQL -> PostgreSQLProvider(dbConfig)
            DatabaseType.MYSQL -> MySQLProvider(dbConfig)
            DatabaseType.MARIADB -> MariaDBProvider(dbConfig)
        }
    }
}