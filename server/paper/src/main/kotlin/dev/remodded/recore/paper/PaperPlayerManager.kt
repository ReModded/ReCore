package dev.remodded.recore.paper

import dev.remodded.recore.api.PlayerManager
import dev.remodded.recore.api.entity.Player
import dev.remodded.recore.paper.entity.wrap
import org.bukkit.Bukkit
import java.util.*

class PaperPlayerManager : PlayerManager {
    override fun getPlayer(id: UUID): Player? {
        return Bukkit.getPlayer(id)?.wrap()
    }

    override fun getPlayer(name: String): Player? {
        return Bukkit.getPlayer(name)?.wrap()
    }

    override fun getPlayers(): Collection<Player> {
        return Bukkit.getOnlinePlayers().map { it.wrap() }
    }
}
