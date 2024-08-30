package dev.remodded.recore.api.command.source

import net.kyori.adventure.text.Component

interface CommandSender {
    fun sendMessage(message: Component)
}
