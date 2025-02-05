package dev.remodded.recore.api.utils.vec

data class Vec2i(
    override var x: Int,
    override var y: Int,
) : Vec2<Int>() {
    constructor(): this(0, 0)

    override fun copy() = Vec2i(x, y)

    override fun plusAssign(other: Vec2<Int>) {
        x += other.x
        y += other.y
    }

    override fun minusAssign(other: Vec2<Int>) {
        x -= other.x
        y -= other.y
    }

    override fun timesAssign(other: Vec2<Int>) {
        x *= other.x
        y *= other.y
    }

    override fun divAssign(other: Vec2<Int>) {
        x /= other.x
        y /= other.y
    }

    override fun lengthSqr(): Int {
        return x * x + y * y
    }
}
