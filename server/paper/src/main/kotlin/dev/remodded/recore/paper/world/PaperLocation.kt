package dev.remodded.recore.paper.world

import dev.remodded.recore.api.world.World
import org.bukkit.Location

data class PaperLocation(
    override val x: Double,
    override val y: Double,
    override val z: Double,
    override val pitch: Double,
    override val yaw: Double,
    override val world: World
) : dev.remodded.recore.api.world.Location {
    constructor(location: Location) : this(
        location.x,
        location.y,
        location.z,
        location.pitch.toDouble(),
        location.yaw.toDouble(),
        PaperWorld(location.world)
    )
}
