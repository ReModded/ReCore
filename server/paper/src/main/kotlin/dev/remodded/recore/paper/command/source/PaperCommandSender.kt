package dev.remodded.recore.paper.command.source

import dev.remodded.recore.api.command.source.CommandSender
import net.kyori.adventure.text.Component

class PaperCommandSender(
    val native: org.bukkit.command.CommandSender
) : CommandSender {
    override fun sendMessage(message: Component) {
        native.sendMessage(message)
    }
}
