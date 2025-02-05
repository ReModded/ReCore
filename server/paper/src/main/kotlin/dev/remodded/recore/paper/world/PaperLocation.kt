package dev.remodded.recore.paper.world

import dev.remodded.recore.api.utils.vec.Vec3
import dev.remodded.recore.api.world.Location
import dev.remodded.recore.api.world.World

data class PaperLocation(
    override val position: Vec3<Double>,
    override val pitch: Double,
    override val yaw: Double,
    override val world: World
) : Location {
    constructor(location: org.bukkit.Location) : this(
        Vec3(location.x, location.y, location.z),
        location.pitch.toDouble(),
        location.yaw.toDouble(),
        location.world.wrap(),
    )
}

fun Location.toNative(): org.bukkit.Location {
    return org.bukkit.Location(world.native(), position.x, position.y, position.z, yaw.toFloat(), pitch.toFloat())
}
