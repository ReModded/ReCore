package dev.remodded.recore.api.utils

import kotlin.math.pow
import kotlin.math.sqrt

data class Position(
    var x: Double,
    var y: Double,
    var z: Double,
) {
    constructor() : this(0.0, 0.0, 0.0)

    fun withX(x: Double) = Position(x, y, z)
    fun withY(y: Double) = Position(x, y, z)
    fun withZ(z: Double) = Position(x, y, z)

    operator fun plusAssign(pos: Position) {
        x += pos.x
        y += pos.y
        z += pos.z
    }
    operator fun plus(pos: Position) = copy().apply { this += pos }

    operator fun minusAssign(pos: Position) {
        x -= pos.x
        y -= pos.y
        z -= pos.z
    }
    operator fun minus(pos: Position) = copy().apply { this -= pos }

    operator fun timesAssign(value: Double) {
        x *= value
        y *= value
        z *= value
    }
    operator fun times(value: Double) = copy().apply { this *= value }

    operator fun divAssign(value: Double) {
        x /= value
        y /= value
        z /= value
    }
    operator fun div(value: Double) = copy().apply { this /= value }

    operator fun unaryMinus() = copy().apply {
        x = -x
        y = -y
        z = -z
    }

    fun distanceTo(pos: Position) = distance(this, pos)
    fun distanceSqrTo(pos: Position) = distanceSqr(this, pos)

    companion object {
        fun distance(pos1: Position, pos2: Position) = sqrt(distanceSqr(pos1, pos2))

        fun distanceSqr(pos1: Position, pos2: Position): Double {
            return (pos1.x - pos2.x).pow(2.0) +
                   (pos1.y - pos2.y).pow(2.0) +
                   (pos1.z - pos2.z).pow(2.0)
        }
    }
}
