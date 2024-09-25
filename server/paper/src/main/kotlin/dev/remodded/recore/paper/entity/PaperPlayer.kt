package dev.remodded.recore.paper.entity

import dev.remodded.recore.api.entity.GameMode
import dev.remodded.recore.api.entity.Player
import net.kyori.adventure.text.Component
import org.bukkit.craftbukkit.entity.CraftPlayer

class PaperPlayer(
    override val native: CraftPlayer
) : PaperEntity(native), Player {

    override val gamemode: GameMode
        get() = when(native.gameMode) {
            org.bukkit.GameMode.SURVIVAL -> GameMode.SURVIVAL
            org.bukkit.GameMode.CREATIVE -> GameMode.CREATIVE
            org.bukkit.GameMode.ADVENTURE -> GameMode.ADVENTURE
            org.bukkit.GameMode.SPECTATOR -> GameMode.SPECTATOR
        }

    override fun sendMessage(message: Component) {
        native.sendMessage(message)
    }
}

fun Player.native() = (this as PaperPlayer).native
fun org.bukkit.entity.Player.wrap() = PaperPlayer(this as CraftPlayer)
