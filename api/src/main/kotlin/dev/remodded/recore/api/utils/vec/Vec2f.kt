package dev.remodded.recore.api.utils.vec

data class Vec2f(
    override var x: Float,
    override var y: Float,
) : Vec2<Float>() {
    constructor(): this(0f, 0f)

    override fun copy() = Vec2f(x, y)

    override fun plusAssign(other: Vec2<Float>) {
        x += other.x
        y += other.y
    }

    override fun minusAssign(other: Vec2<Float>) {
        x -= other.x
        y -= other.y
    }

    override fun timesAssign(other: Vec2<Float>) {
        x *= other.x
        y *= other.y
    }

    override fun divAssign(other: Vec2<Float>) {
        x /= other.x
        y /= other.y
    }

    override fun lengthSqr(): Float {
        return x * x + y * y
    }
}
