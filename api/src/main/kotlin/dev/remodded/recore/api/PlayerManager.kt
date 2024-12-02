package dev.remodded.recore.api

import dev.remodded.recore.api.entity.Player
import java.util.*

interface PlayerManager {
    fun getPlayer(id: UUID): Player?
    fun getPlayer(name: String): Player?

    fun getPlayers(): Collection<Player>
}
