package dev.remodded.recore.api.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import dev.remodded.recore.api.command.source.CommandSrcStack

object CommandUtils {
    @JvmStatic
    fun literal(name: String): LiteralArgumentBuilder<CommandSrcStack> {
        return LiteralArgumentBuilder.literal(name)
    }

    @JvmStatic
    fun <T>argument(name: String, arg: ArgumentType<T>): RequiredArgumentBuilder<CommandSrcStack, T> {
        return RequiredArgumentBuilder.argument(name, arg)
    }

    fun <T: ArgumentBuilder<CommandSrcStack, T>> ArgumentBuilder<CommandSrcStack, T>.requirePermission(permission: String): T {
        return requires { it.sender.hasPermission(permission) }
    }
}
