package dev.remodded.recore.paper.command.source

import dev.remodded.recore.api.command.source.CommandSender

class PaperCommandSender(
    val native: org.bukkit.command.CommandSender
) : CommandSender {
    override fun sendMessage(message: String) {
        native.sendMessage(message)
    }
}
