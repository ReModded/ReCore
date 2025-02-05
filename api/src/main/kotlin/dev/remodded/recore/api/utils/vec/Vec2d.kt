package dev.remodded.recore.api.utils.vec

data class Vec2d(
    override var x: Double,
    override var y: Double,
) : Vec2<Double>() {
    constructor(): this(0.0, 0.0)

    override fun copy() = Vec2d(x, y)

    override fun plusAssign(other: Vec2<Double>) {
        x += other.x
        y += other.y
    }

    override fun minusAssign(other: Vec2<Double>) {
        x -= other.x
        y -= other.y
    }

    override fun timesAssign(other: Vec2<Double>) {
        x *= other.x
        y *= other.y
    }

    override fun divAssign(other: Vec2<Double>) {
        x /= other.x
        y /= other.y
    }

    override fun lengthSqr(): Double {
        return x * x + y * y
    }
}
