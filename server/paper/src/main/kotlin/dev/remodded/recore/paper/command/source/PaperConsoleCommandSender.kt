package dev.remodded.recore.paper.command.source

import dev.remodded.recore.api.command.source.ConsoleCommandSender

class PaperConsoleCommandSender(
    override val native: org.bukkit.command.ConsoleCommandSender
) : PaperCommandSender(native), ConsoleCommandSender

fun org.bukkit.command.ConsoleCommandSender.wrap() = PaperConsoleCommandSender(this)
fun ConsoleCommandSender.native() = (this as PaperConsoleCommandSender).native
