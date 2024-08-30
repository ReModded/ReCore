package dev.remodded.recore.api.world

import dev.remodded.recore.api.utils.Position

interface Location {
    val position: Position
    val pitch: Double
    val yaw: Double
    val world: World
}
