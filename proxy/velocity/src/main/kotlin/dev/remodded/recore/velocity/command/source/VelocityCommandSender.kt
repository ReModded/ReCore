package dev.remodded.recore.velocity.command.source

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.ConsoleCommandSource
import com.velocitypowered.api.proxy.Player
import dev.remodded.recore.api.command.source.CommandSender
import dev.remodded.recore.velocity.entity.VelocityPlayer
import dev.remodded.recore.velocity.entity.wrap
import net.kyori.adventure.text.Component

open class VelocityCommandSender(
    open val native: CommandSource
) : CommandSender {
    override fun sendMessage(message: Component) {
        native.sendMessage(message)
    }
}


fun CommandSender.native(): CommandSource = when(this) {
    is VelocityPlayer -> this.native
    is VelocityCommandSender -> this.native
    else -> throw IllegalArgumentException("Unknown CommandSender type")
}
fun CommandSource.wrap(): CommandSender = when(this) {
    is Player -> this.wrap()
    is ConsoleCommandSource -> this.wrap()
    else -> VelocityCommandSender(this)
}
