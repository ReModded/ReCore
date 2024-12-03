package dev.remodded.recore.test

import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.Server
import dev.remodded.recore.api.cache.CacheProvider
import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.config.ConfigLoader
import dev.remodded.recore.api.database.DatabaseProvider
import dev.remodded.recore.api.messaging.MessagingManager
import dev.remodded.recore.api.plugins.PluginInfo
import dev.remodded.recore.api.plugins.PluginsManager
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.service.ServiceProvider
import dev.remodded.recore.api.utils.getFieldAccess
import org.slf4j.Logger

open class ReCoreTest : ReCore {
    init {
        ReCore.Companion::class.java.getFieldAccess("_instance").set(null, this)
    }

    override val server: Server
        get() = TestServer()

    override val serviceProvider: ServiceProvider
        get() = TODO("Not yet implemented")
    override val commandManager: CommandManager
        get() = TODO("Not yet implemented")
    override val messagingManager: MessagingManager
        get() = TODO("Not yet implemented")
    override val pluginsManager: PluginsManager
        get() = TODO("Not yet implemented")
    override val cacheProvider: CacheProvider
        get() = TODO("Not yet implemented")
    override val databaseProvider: DatabaseProvider
        get() = TODO("Not yet implemented")

    override fun <T> createConfigLoader(
        plugin: ReCorePlugin,
        configClass: Class<T>
    ): ConfigLoader<T> {
        TODO("Not yet implemented")
    }

    override val logger: Logger
        get() = TODO("Not yet implemented")

    override fun getPluginInfo(): PluginInfo {
        TODO("Not yet implemented")
    }
}
