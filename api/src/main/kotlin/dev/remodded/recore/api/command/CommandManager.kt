package dev.remodded.recore.api.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.command.source.CommandSrcStack
import dev.remodded.recore.api.command.source.ConsoleCommandSender
import dev.remodded.recore.api.plugins.ReCorePlugin

interface CommandManager {
    fun registerCommand(plugin: ReCorePlugin, command: LiteralArgumentBuilder<CommandSrcStack>, vararg aliases: String)

    fun executeCommand(command: String): Int

    fun executeCommand(command: String, sender: CommandSender): Int

    fun getPermission(plugin: ReCorePlugin, command: String): String

    fun consoleSender(): ConsoleCommandSender
}
