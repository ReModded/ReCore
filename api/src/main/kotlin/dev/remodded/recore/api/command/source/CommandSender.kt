package dev.remodded.recore.api.command.source

import net.kyori.adventure.text.Component

interface CommandSender {
    fun hasPermission(permission: String): Boolean

    fun sendMessage(message: Component)
}
