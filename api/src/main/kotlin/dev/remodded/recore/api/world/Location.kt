package dev.remodded.recore.api.world

interface Location {
    val x: Double
    val y: Double
    val z: Double
    val pitch: Double
    val yaw: Double
    val world: World
}
