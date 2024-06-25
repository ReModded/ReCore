package dev.remodded.recore.velocity.command

import dev.remodded.recore.api.command.source.CommandSender
import net.kyori.adventure.text.Component

class VelocityCommandSender(
    val sender: com.velocitypowered.api.command.CommandSource
) : CommandSender {
    override fun sendMessage(message: String) {
        sender.sendMessage(Component.text(message))
    }
}
