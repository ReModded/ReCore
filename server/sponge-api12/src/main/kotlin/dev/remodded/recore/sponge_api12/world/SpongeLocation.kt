package dev.remodded.recore.sponge_api12.world

import dev.remodded.recore.api.world.Location
import dev.remodded.recore.api.world.World
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec2
import net.minecraft.world.phys.Vec3

class SpongeLocation(
    override val x: Double,
    override val y: Double,
    override val z: Double,
    override val pitch: Double,
    override val yaw: Double,
    override val world: World
) : Location {
    constructor(worldPosition: Vec3, rotation: Vec2, level: Level) : this(
        worldPosition.x, worldPosition.y, worldPosition.z,
        rotation.x.toDouble(), rotation.y.toDouble(),
        level as World
    )

    constructor(worldPosition: Vec3, pitch: Double, yaw: Double, level: Level) : this(
        worldPosition.x, worldPosition.y, worldPosition.z,
        pitch, yaw,
        level as World
    )
}
