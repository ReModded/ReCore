package dev.remodded.recore.velocity.entity

import dev.remodded.recore.api.entity.GameMode
import dev.remodded.recore.api.entity.Player
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.utils.red
import dev.remodded.recore.api.utils.text
import dev.remodded.recore.api.world.Location
import dev.remodded.recore.velocity.exception.UnsupportedOnProxyException
import net.kyori.adventure.text.Component
import java.util.*

class VelocityPlayer(
    val native: com.velocitypowered.api.proxy.Player
) : Player {
    override val gamemode: GameMode get() = GameMode.NONE

    override fun kick(message: Component?) {
        native.disconnect(message ?: "You has been kicked".red())
    }

    override val id: UUID get() = native.uniqueId
    override val name: String get() = native.username
    override val displayName get() = name.text()

    override fun sendMessage(message: Component) {
        native.sendMessage(message)
    }

    override val location: Location get() = throw UnsupportedOnProxyException("VelocityPlayer.location")
    override fun teleport(location: Location) = throw UnsupportedOnProxyException("VelocityPlayer.teleport")

    // Additional Data Holder delegation
    override fun getAllAdditionalData() = throw UnsupportedOnProxyException("VelocityPlayer.getAllAdditionalData")
    override fun getAdditionalData(plugin: ReCorePlugin) = throw UnsupportedOnProxyException("VelocityPlayer.getAdditionalData")
    override fun removeAdditionalData(plugin: ReCorePlugin) = throw UnsupportedOnProxyException("VelocityPlayer.removeAdditionalData")
    override fun clearAdditionalData() = throw UnsupportedOnProxyException("VelocityPlayer.clearAdditionalData")
}

fun Player.native(): com.velocitypowered.api.proxy.Player = (this as VelocityPlayer).native
fun com.velocitypowered.api.proxy.Player.wrap(): Player = VelocityPlayer(this)
