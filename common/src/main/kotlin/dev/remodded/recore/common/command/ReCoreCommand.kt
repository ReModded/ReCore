package dev.remodded.recore.common.command

import com.mojang.brigadier.context.CommandContext
import dev.remodded.recore.api.ReCoreAPI
import dev.remodded.recore.api.command.CommandUtils.argument
import dev.remodded.recore.api.command.CommandUtils.literal
import dev.remodded.recore.api.command.arguments.EnumArgumentType
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.common.Constants

object ReCoreCommand {
    fun register() {
        val manager = ReCoreAPI.INSTANCE.commandManager

        manager.registerCommand(ReCoreAPI.INSTANCE.platform.getPluginInfo(), command(), "rc")
    }

    private fun command() =
        literal("recore")
            .then(literal("reload")
                .executes { ctx -> reload(ctx, ReloadType.ALL) }
                .then(argument("type", EnumArgumentType.get())
                    .suggests(EnumArgumentType.enum<ReloadType>())
                    .executes { ctx -> reload(ctx, EnumArgumentType.getEnum<ReloadType>(ctx, "type")) }
                )
            )
            .then(literal("version")
                .executes { ctx -> version(ctx) }
            )
            .executes { ctx -> version(ctx) }

    private fun reload(ctx: CommandContext<CommandSrcStack>, type: ReloadType): Int {
        ctx.source.sender.sendMessage("Test reload $type")
        return 1
    }

    private fun version(ctx: CommandContext<CommandSrcStack>): Int {
        ctx.source.sender.sendMessage("ReCore version: " + Constants.VERSION)
        return 1
    }

    @Suppress("unused")
    private enum class ReloadType {
        ALL,
        CONFIG,
        DATABASE,
    }
}
