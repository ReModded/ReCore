package dev.remodded.recore.paper.world

import dev.remodded.recore.api.utils.Position
import dev.remodded.recore.api.world.Location
import dev.remodded.recore.api.world.World

data class PaperLocation(
    override val position: Position,
    override val pitch: Double,
    override val yaw: Double,
    override val world: World
) : Location {
    constructor(location: org.bukkit.Location) : this(
        Position(location.x, location.y, location.z),
        location.pitch.toDouble(),
        location.yaw.toDouble(),
        location.world.wrap()
    )
}
