package dev.remodded.recore.velocity.entity

import dev.remodded.recore.api.entity.GameMode
import dev.remodded.recore.api.entity.Player
import dev.remodded.recore.api.utils.text
import dev.remodded.recore.api.world.Location
import net.kyori.adventure.text.Component
import java.util.*

class VelocityPlayer(
    val native: com.velocitypowered.api.proxy.Player
) : Player {
    override val gamemode: GameMode get() = GameMode.NONE
    override val id: UUID get() = native.uniqueId
    override val name get() = native.username.text()

    override val location: Location get() = throw UnsupportedOperationException("VelocityPlayer.location is not supported")

    override fun sendMessage(message: Component) {
        native.sendMessage(message)
    }
}

fun Player.native(): com.velocitypowered.api.proxy.Player = (this as VelocityPlayer).native
fun com.velocitypowered.api.proxy.Player.wrap(): Player = VelocityPlayer(this)
