package dev.remodded.recore.velocity.command

import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.velocity.entity.wrap
import net.kyori.adventure.text.Component

class VelocityCommandSender(
    val native: com.velocitypowered.api.command.CommandSource
) : CommandSender {
    override fun sendMessage(message: Component) {
        native.sendMessage(message)
    }
}


fun CommandSender.native(): com.velocitypowered.api.command.CommandSource = when(this) {
    is dev.remodded.recore.velocity.entity.VelocityPlayer -> this.native
    is VelocityCommandSender -> this.native
    else -> throw IllegalArgumentException("Unknown CommandSender type")
}
fun com.velocitypowered.api.command.CommandSource.wrap(): CommandSender = when(this) {
    is com.velocitypowered.api.proxy.Player -> this.wrap()
    else -> VelocityCommandSender(this)
}
