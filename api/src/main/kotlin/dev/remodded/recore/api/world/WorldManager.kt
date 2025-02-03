package dev.remodded.recore.api.world

import dev.remodded.recore.api.resources.ResourceKey
import java.util.*

interface WorldManager {
    fun createWorld(config: WorldCreateConfig): World

    fun getWorld(uuid: UUID): World?
    fun getWorld(id: ResourceKey): World?
}
