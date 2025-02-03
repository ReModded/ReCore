package dev.remodded.recore.api.world

import dev.remodded.recore.api.resources.ResourceKey

data class WorldCreateConfig(
    var key: ResourceKey,

    var type: WorldType = WorldType.NORMAL,
    var generatorSettings: String = "{}",
)
