package dev.remodded.recore.api.world

import java.util.*

interface World {
    val id: UUID
    val name: String

    var spawnLocation: Location

    fun unload(save: Boolean): Boolean
}
