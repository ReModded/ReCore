package dev.remodded.recore.test

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.api.messaging.MessagingChannelType
import dev.remodded.recore.api.messaging.MessagingManager
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.api.plugins.PluginInfo
import dev.remodded.recore.common.Constants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.io.path.Path

class ReCoreTestPlatform : ReCorePlatform {
    override val platformInfo = PlatformInfo(
        platform = Platform.None,
        platformName = "ReCoreTestPlatform",
        platformVersion = "1.0.0",
        mcVersion = "1.19.3",
        dataFolder = Path("test")
    )

    override val libraryLoader: LibraryLoader
        get() = TODO("Not yet implemented")
    override val commandManager = object : CommandManager {
            override fun registerCommand(
                pluginInfo: PluginInfo,
                command: LiteralArgumentBuilder<CommandSrcStack>,
                vararg aliases: String
            ) {
                TODO("Not yet implemented")
            }

            override fun executeCommand(command: String): Int {
                TODO("Not yet implemented")
            }

            override fun executeCommand(
                command: String,
                sender: CommandSender
            ): Int {
                TODO("Not yet implemented")
            }

            override fun getPermission(
                plugin: PluginInfo,
                command: String
            ): String {
                TODO("Not yet implemented")
            }
        }

    override fun createChannelMessagingManager(): MessagingManager {
        return object : MessagingManager {
            override val type: MessagingChannelType
                get() = TODO("Not yet implemented")

            override fun <T> getChannel(
                channel: String,
                clazz: Class<T>
            ): MessageChannel<T> {
                TODO("Not yet implemented")
            }
        }
    }

    override val logger: Logger = LoggerFactory.getLogger(ReCoreTestPlatform::class.java)

    override fun getPluginInfo() = PluginInfo(
        Constants.ID,
        Constants.NAME,
        Constants.VERSION,
        this,
    )
}
