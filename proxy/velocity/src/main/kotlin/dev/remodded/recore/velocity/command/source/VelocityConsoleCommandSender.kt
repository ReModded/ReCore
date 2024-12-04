package dev.remodded.recore.velocity.command.source

import com.velocitypowered.api.proxy.ConsoleCommandSource
import dev.remodded.recore.api.command.source.ConsoleCommandSender

class VelocityConsoleCommandSender(
    override val native: ConsoleCommandSource,
) : VelocityCommandSender(native), ConsoleCommandSender

fun ConsoleCommandSource.wrap() = VelocityConsoleCommandSender(this)
fun ConsoleCommandSender.native() = (this as VelocityConsoleCommandSender).native

