package dev.remodded.recore.common.command

import com.mojang.brigadier.context.CommandContext
import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.command.CommandUtils.argument
import dev.remodded.recore.api.command.CommandUtils.literal
import dev.remodded.recore.api.command.arguments.EnumArgumentType
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.messaging.MessagingManager.Companion.getChannel
import dev.remodded.recore.api.utils.plus
import dev.remodded.recore.api.utils.text
import dev.remodded.recore.common.Constants

object ReCoreCommand {

    private const val TEST_CHANNEL = "TEST_CHANNEL"

    fun register() {
        val manager = ReCore.INSTANCE.commandManager


        val ch = ReCore.INSTANCE.messagingManager.getChannel<String?>(TEST_CHANNEL)

        ch.registerListener { msg ->
            println("Received: $msg")
        }

        manager.registerCommand(ReCore.INSTANCE.getPluginInfo(), command(), "rc")
    }

    private fun command() =
        literal("recore")
            .then(literal("test")
                .executes(::test)
            )
            .then(literal("reload")
                .executes { ctx -> reload(ctx, ReloadType.ALL) }
                .then(argument("type", EnumArgumentType.get())
                    .suggests(EnumArgumentType.enum<ReloadType>())
                    .executes { ctx -> reload(ctx, EnumArgumentType.getEnum<ReloadType>(ctx, "type")) }
                )
            )
            .then(literal("version")
                .executes(::version)
            )
            .executes(::version)

    private fun test(ctx: CommandContext<CommandSrcStack>): Int {
        val ch = ReCore.INSTANCE.messagingManager.getChannel<String>(TEST_CHANNEL)
        if (ch.sendMessage("Hello World"))
            ctx.source.sender.sendMessage("Message sent".text())
        else
            ctx.source.sender.sendMessage("Message not sent".text())
        return 1
    }

    private fun reload(ctx: CommandContext<CommandSrcStack>, type: ReloadType): Int {
        ctx.source.sender.sendMessage("Test reload $type".text())
        return 1
    }

    private fun version(ctx: CommandContext<CommandSrcStack>): Int {
        ctx.source.sender.sendMessage("ReCore version: ".text() + Constants.VERSION)
        return 1
    }

    @Suppress("unused")
    private enum class ReloadType {
        ALL,
        CONFIG,
        DATABASE,
    }
}
