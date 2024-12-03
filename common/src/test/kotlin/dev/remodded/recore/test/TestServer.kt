package dev.remodded.recore.test

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.remodded.recore.api.PlayerManager
import dev.remodded.recore.api.Server
import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.command.source.ConsoleCommandSender
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.common.command.CommonCommandManager
import kotlin.io.path.Path

class TestServer : Server {
    override val playerManager: PlayerManager
        get() = TODO("Not yet implemented")

    override val platformInfo = PlatformInfo(
        platform = Platform.None,
        platformName = "ReCoreTestPlatform",
        platformVersion = "1.0.0",
        mcVersion = "1.19.3",
        dataFolder = Path("test")
    )

    override val libraryLoader: LibraryLoader
        get() = TODO("Not yet implemented")
    override val commandManager = object : CommonCommandManager() {
            override fun registerCommand(
                plugin: ReCorePlugin,
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

        override fun consoleSender(): ConsoleCommandSender {
            TODO("Not yet implemented")
        }
    }
}
