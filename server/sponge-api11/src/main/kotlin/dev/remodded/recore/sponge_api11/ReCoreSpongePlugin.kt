package dev.remodded.recore.sponge_api11

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
import org.slf4j.LoggerFactory
import org.spongepowered.api.Sponge
import java.nio.file.Paths

class ReCoreSpongePlugin(
    private val libraryLoader: LibraryLoader
) : ReCorePlugin {

    private val logger = LoggerFactory.getLogger("ReCore")
    private lateinit var databaseProvider: DatabaseProvider
    private lateinit var config: ReCoreConfig

    init {
        loadConfig()
        initDatabase()
        ReCore.INSTANCE = this
    }
    override fun getLibraryLoader(): LibraryLoader = libraryLoader

    override fun getPlatformInfo(): PlatformInfo {
        val serverPlatform = Sponge.platform()
        val spongeContainer =
            serverPlatform.container(org.spongepowered.api.Platform.Component.IMPLEMENTATION).metadata()

        return PlatformInfo(
            Platform.SPONGE_API11,
            spongeContainer.name().orElse("Sponge-API11 (Unknown)"),
            spongeContainer.version().toString(),
            serverPlatform.minecraftVersion().name()
        )
    }

    override fun <T> getConfigLoader(pluginName: String, configClass: Class<T>): ConfigManager<T> {
        val path = Paths.get("./config")
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
            logger.info("Disabling plugin")
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
