package dev.remodded.recore.velocity

import com.velocitypowered.api.proxy.ProxyServer
import dev.remodded.recore.api.PlayerManager
import dev.remodded.recore.api.entity.Player
import dev.remodded.recore.velocity.entity.wrap
import java.util.*
import kotlin.jvm.optionals.getOrNull

class VelocityPlayerManager(val proxy: ProxyServer) : PlayerManager {
    override fun getPlayer(id: UUID): Player? {
        return proxy.getPlayer(id).getOrNull()?.wrap()
    }

    override fun getPlayer(name: String): Player? {
        return proxy.getPlayer(name).getOrNull()?.wrap()
    }

    override fun getPlayers(): Collection<Player> {
        return proxy.allPlayers.map { it.wrap() }
    }
}
