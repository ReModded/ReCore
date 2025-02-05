package dev.remodded.recore.api.utils.vec

data class Vec3i(
    override var x: Int,
    override var y: Int,
    override var z: Int,
) : Vec3<Int>() {
    constructor(): this(0, 0, 0)

    override fun copy() = Vec3i(x, y, z)

    override fun plusAssign(other: Vec3<Int>) {
        x += other.x
        y += other.y
        z += other.z
    }

    override fun minusAssign(other: Vec3<Int>) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    override fun timesAssign(other: Vec3<Int>) {
        x *= other.x
        y *= other.y
        z *= other.z
    }

    override fun divAssign(other: Vec3<Int>) {
        x /= other.x
        y /= other.y
        z /= other.z
    }

    override fun lengthSqr(): Int {
        return x * x + y * y + z * z
    }
}
