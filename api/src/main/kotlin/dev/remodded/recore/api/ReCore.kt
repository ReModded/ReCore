package dev.remodded.recore.api

import dev.remodded.recore.api.cache.CacheProvider
import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.config.ConfigManager
import dev.remodded.recore.api.database.DatabaseProvider
import dev.remodded.recore.api.messaging.MessagingManager
import dev.remodded.recore.api.plugins.PluginInfo
import dev.remodded.recore.api.plugins.PluginsManager

interface ReCore {

    companion object {
        @JvmStatic
        lateinit var INSTANCE: ReCore
    }

    val platform: ReCorePlatform


    val commandManager: CommandManager

    val messagingManager: MessagingManager

    val pluginsManager: PluginsManager


    val cacheProvider: CacheProvider
    val databaseProvider: DatabaseProvider

    fun <T>createConfigLoader(pluginInfo: PluginInfo, configClass: Class<T>): ConfigManager<T>
}
