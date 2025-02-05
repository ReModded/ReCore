package dev.remodded.recore.api.utils.vec

data class Vec3f(
    override var x: Float,
    override var y: Float,
    override var z: Float,
) : Vec3<Float>() {
    constructor(): this(0f, 0f, 0f)

    override fun copy() = Vec3f(x, y, z)

    override fun plusAssign(other: Vec3<Float>) {
        x += other.x
        y += other.y
        z += other.z
    }

    override fun minusAssign(other: Vec3<Float>) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    override fun timesAssign(other: Vec3<Float>) {
        x *= other.x
        y *= other.y
        z *= other.z
    }

    override fun divAssign(other: Vec3<Float>) {
        x /= other.x
        y /= other.y
        z /= other.z
    }

    override fun lengthSqr(): Float {
        return x * x + y * y + z * z
    }
}
