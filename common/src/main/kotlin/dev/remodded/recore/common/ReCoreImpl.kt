package dev.remodded.recore.common

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.ReCoreAPI
import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.cache.CacheProvider
import dev.remodded.recore.api.cache.CacheType
import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.config.DatabaseType
import dev.remodded.recore.api.database.DatabaseProvider
import dev.remodded.recore.api.messaging.MessagingChannelType
import dev.remodded.recore.api.messaging.MessagingManager
import dev.remodded.recore.api.plugins.PluginInfo
import dev.remodded.recore.api.plugins.PluginsManager
import dev.remodded.recore.common.cache.database.DatabaseCacheProvider
import dev.remodded.recore.common.cache.redis.RedisCacheProvider
import dev.remodded.recore.common.command.ReCoreCommand
import dev.remodded.recore.common.config.DefaultConfigManager
import dev.remodded.recore.common.config.ReCoreConfig
import dev.remodded.recore.common.database.MariaDBProvider
import dev.remodded.recore.common.database.MySQLProvider
import dev.remodded.recore.common.database.PostgreSQLProvider
import dev.remodded.recore.common.messaging.redis.RedisMessagingManager
import dev.remodded.recore.common.plugins.CommonPluginsManager
import org.slf4j.LoggerFactory

class ReCoreImpl (
    override val platform: ReCorePlatform,
) : ReCore {

    val config: ReCoreConfig

    override val cacheProvider: CacheProvider
    override val databaseProvider: DatabaseProvider

    override val commandManager: CommandManager get() = platform.commandManager

    override val pluginsManager: PluginsManager
    override val messagingManager: MessagingManager


    init {
        printPlatformInfo()

        config = loadConfig()

        databaseProvider = initDatabase()
        cacheProvider = initCache()

        pluginsManager = CommonPluginsManager()
        messagingManager = initMessagingManager()
    }

    fun init() {
        registerCommands()
    }

    override fun <T> createConfigLoader(pluginInfo: PluginInfo, configClass: Class<T>) =
        DefaultConfigManager(platform.platformInfo.dataFolder.resolve(pluginInfo.id), configClass)


    companion object {
        @JvmStatic
        lateinit var INSTANCE: ReCoreImpl

        private val logger = LoggerFactory.getLogger(Constants.NAME)

        fun init(platform: ReCorePlatform) {
            logger.info("ReCore Initializing")
            val instance = ReCoreImpl(platform)

            INSTANCE = instance
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
            val cfg = createConfigLoader(platform.getPluginInfo(), ReCoreConfig::class.java).getConfig("ReCore.conf")!!
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

    private fun initCache(): CacheProvider {
        val cache = config.cache
        return when(cache.type) {
            CacheType.REDIS -> RedisCacheProvider()
            CacheType.DATABASE -> DatabaseCacheProvider()
        }
    }

    private fun initMessagingManager(): MessagingManager{
        val messagingService = config.messagingService
       return when (messagingService) {
            MessagingChannelType.CHANNELS -> platform.createChannelMessagingManager()
            MessagingChannelType.REDIS -> RedisMessagingManager()
            MessagingChannelType.POSTGRES -> TODO()
       }
    }

    private fun registerCommands() {
        ReCoreCommand.register()
    }
}
