package dev.remodded.recore.sponge_api12

import dev.remodded.recore.api.PlayerManager
import dev.remodded.recore.api.entity.Player
import org.spongepowered.api.Sponge
import java.util.*
import kotlin.jvm.optionals.getOrNull

class SpongePlayerManager : PlayerManager {
    override fun getPlayer(id: UUID): Player? {
        return Sponge.server().player(id).getOrNull() as? Player
    }

    override fun getPlayer(name: String): Player? {
        return Sponge.server().player(name).getOrNull() as? Player
    }

    override fun getPlayers(): Collection<Player> {
        @Suppress("UNCHECKED_CAST")
        return Sponge.server().onlinePlayers() as Collection<Player>
    }
}
