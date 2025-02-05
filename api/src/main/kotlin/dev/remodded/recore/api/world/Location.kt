package dev.remodded.recore.api.world

import dev.remodded.recore.api.utils.vec.Vec3

interface Location {
    val position: Vec3<Double>
    val pitch: Double
    val yaw: Double
    val world: World

    companion object {
        fun of(position: Vec3<Double>, world: World) =
            of(position, 0.0, 0.0, world)

        fun of(position: Vec3<Double>, pitch: Double, yaw: Double, world: World): Location {
            class Location(
                override val position: Vec3<Double>,
                override val pitch: Double,
                override val yaw: Double,
                override val world: World
            ) : dev.remodded.recore.api.world.Location

            return Location(position, pitch, yaw, world)
        }
    }
}
