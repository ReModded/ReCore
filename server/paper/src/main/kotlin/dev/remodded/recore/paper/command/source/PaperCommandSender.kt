package dev.remodded.recore.paper.command.source

import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.api.entity.Player
import dev.remodded.recore.paper.entity.native
import dev.remodded.recore.paper.entity.wrap
import net.kyori.adventure.text.Component

open class PaperCommandSender(
    open val native: org.bukkit.command.CommandSender
) : CommandSender {
    override fun sendMessage(message: Component) {
        native.sendMessage(message)
    }
}

fun CommandSender.native(): org.bukkit.command.CommandSender = when(this) {
    is Player -> this.native()
    is PaperConsoleCommandSender -> this.native
    is PaperCommandSender -> this.native
    else -> throw IllegalArgumentException("Unknown CommandSender type")
}
fun org.bukkit.command.CommandSender.wrap(): CommandSender = when(this) {
    is org.bukkit.entity.Player -> this.wrap()
    is org.bukkit.command.ConsoleCommandSender -> this.wrap()
    else -> PaperCommandSender(this)
}
