package dev.remodded.recore.api.utils.vec

data class Vec3d(
    override var x: Double,
    override var y: Double,
    override var z: Double,
) : Vec3<Double>() {
    constructor(): this(0.0, 0.0, 0.0)

    override fun copy() = Vec3d(x, y, z)

    override fun plusAssign(other: Vec3<Double>) {
        x += other.x
        y += other.y
        z += other.z
    }

    override fun minusAssign(other: Vec3<Double>) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    override fun timesAssign(other: Vec3<Double>) {
        x *= other.x
        y *= other.y
        z *= other.z
    }

    override fun divAssign(other: Vec3<Double>) {
        x /= other.x
        y /= other.y
        z /= other.z
    }

    override fun lengthSqr(): Double {
        return x * x + y * y + z * z
    }
}
