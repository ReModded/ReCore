package dev.remodded.recore.common

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.ReCoreAPI
import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.database.DatabaseProvider
import dev.remodded.recore.common.command.ReCoreCommand
import dev.remodded.recore.common.config.DefaultConfigManager
import dev.remodded.recore.common.config.ReCoreConfig
import org.slf4j.LoggerFactory

class ReCoreImpl (
    override val platform: ReCorePlatform
) : ReCore {

    private val config: ReCoreConfig
    override val databaseProvider: DatabaseProvider?


    override val commandManager: CommandManager get() = platform.commandManager


    init {
        printPlatformInfo()

        config = loadConfig()
        databaseProvider = initDatabase()
    }

    fun init() {
        registerCommands()
    }

    override fun <T> createConfigLoader(pluginName: String, configClass: Class<T>) =
        DefaultConfigManager(platform.platformInfo.dataFolder.resolve(pluginName), configClass)


    companion object {
        private val logger = LoggerFactory.getLogger("ReCore")

        fun init(platform: ReCorePlatform) {
            logger.info("ReCore Initializing")
            val instance = ReCoreImpl(platform)
            ReCoreAPI.INSTANCE = instance
            instance.init()
        }
    }

    private fun printPlatformInfo() {
        val platformInfo = platform.platformInfo

        val info = listOf(
            "ReCore (${Constants.VERSION})",
            "Platform: ${platformInfo.platform}",
            "Platform Name: ${platformInfo.platformName}",
            "Platform Version: ${platformInfo.platformVersion}",
            "Minecraft Version: ${platformInfo.mcVersion}",
            "Config Directory: '${platformInfo.dataFolder.toAbsolutePath().normalize()}'"
        )
        val len = info.maxOf { it.length } + 4
        val header = "Platform Info"

        logger.info("#".repeat(len + 2))
        logger.info("#  $header" + " ".repeat(len - header.length - 4) + "  #")
        logger.info("#".repeat(len + 2))
        for (line in info)
            logger.info("#  $line" + " ".repeat(len - line.length - 4) + "  #")
        logger.info("#".repeat(len + 2))
    }


    private fun loadConfig(): ReCoreConfig {
        try {
            logger.info("Loading configuration")
            val cfg = createConfigLoader(Constants.NAME, ReCoreConfig::class.java).getConfig("ReCore.conf")!!
            logger.info("Config ReCore.conf loaded successfully")
            return cfg
        } catch (e: Exception) {
            logger.error("Error while loading ReCore.conf", e)
            logger.info("Disabling plugin")

            return ReCoreConfig()
        }
    }

    private fun initDatabase(): DatabaseProvider? {
        val dbConfig = config.database
//        return when(dbConfig.databaseType) {
//            DatabaseType.POSTGRESQL -> PostgreSQLProvider(dbConfig)
//            DatabaseType.MYSQL -> MySQLProvider(dbConfig)
//            DatabaseType.MARIADB -> MariaDBProvider(dbConfig)
//        }
        return null
    }

    private fun registerCommands() {
        ReCoreCommand.register()
    }
}
