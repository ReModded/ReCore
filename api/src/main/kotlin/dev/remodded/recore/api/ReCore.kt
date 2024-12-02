package dev.remodded.recore.api

import dev.remodded.recore.api.cache.CacheProvider
import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.config.ConfigLoader
import dev.remodded.recore.api.database.DatabaseProvider
import dev.remodded.recore.api.messaging.MessagingManager
import dev.remodded.recore.api.plugins.PluginsManager
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.service.ServiceProvider

interface ReCore : ReCorePlugin {

    companion object {
        @JvmStatic
        val INSTANCE: ReCore get() = _instance

        private lateinit var _instance: ReCore
    }

    val serviceProvider: ServiceProvider

    val commandManager: CommandManager

    val messagingManager: MessagingManager

    val pluginsManager: PluginsManager

    val cacheProvider: CacheProvider
    val databaseProvider: DatabaseProvider

    val server: Server

    fun <T>createConfigLoader(plugin: ReCorePlugin, configClass: Class<T>): ConfigLoader<T>
}
