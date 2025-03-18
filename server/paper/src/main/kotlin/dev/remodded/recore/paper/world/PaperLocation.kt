package dev.remodded.recore.paper.world

import dev.remodded.recore.api.utils.vec.Vec3
import dev.remodded.recore.api.world.Location
import dev.remodded.recore.api.world.World
import dev.remodded.recore.common.utils.BiMapper

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

    object Mapper : BiMapper<org.bukkit.Location, Location> {
        override fun map(value: org.bukkit.Location): Location = PaperLocation(value)
        override fun unmap(value: Location): org.bukkit.Location = value.toNative()
    }
}

fun Location.toNative(): org.bukkit.Location {
    return org.bukkit.Location(world.native(), position.x, position.y, position.z, yaw.toFloat(), pitch.toFloat())
}
