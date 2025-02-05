package dev.remodded.recore.api.utils.vec

import kotlin.math.sqrt

abstract class Vec3<T: Number> : Vec<T> {
    abstract var x: T
    abstract var y: T
    abstract var z: T

    companion object {
        inline fun<reified T: Number> from(): Vec3<T> = from(T::class.java)
        fun<T: Number> from(clazz: Class<T>): Vec3<T> {
            @Suppress("UNCHECKED_CAST")
            return when(clazz) {
                Double::class.javaPrimitiveType -> Vec3d()
                Float::class.javaPrimitiveType -> Vec3f()
                Int::class.javaPrimitiveType -> Vec3i()
                else -> throw IllegalArgumentException("Unsupported class $clazz")
            } as Vec3<T>
        }

        fun <T: Number> from(x: T, y: T, z: T): Vec3<T> {
            return from(x.javaClass).apply {
                this.x = x
                this.y = y
                this.z = z
            }
        }

        operator fun <T: Number> invoke(x: T, y: T, z: T) = from(x, y, z)
    }

    abstract override fun copy(): Vec3<T>

    abstract operator fun plusAssign(other: Vec3<T>)
    abstract operator fun minusAssign(other: Vec3<T>)
    abstract operator fun timesAssign(other: Vec3<T>)
    abstract operator fun divAssign(other: Vec3<T>)

    operator fun plus(other: Vec3<T>): Vec3<T> {
        val result = copy()
        result += other
        return result
    }
    operator fun minus(other: Vec3<T>): Vec3<T> {
        val result = copy()
        result -= other
        return result
    }
    operator fun times(other: Vec3<T>): Vec3<T> {
        val result = copy()
        result *= other
        return result
    }
    operator fun div(other: Vec3<T>): Vec3<T> {
        val result = copy()
        result /= other
        return result
    }

    abstract override fun lengthSqr(): T
    override fun length(): Double {
        return sqrt(lengthSqr().toDouble())
    }

    override fun toInt(): Vec3<Int> {
        return Vec3i(x.toInt(), y.toInt(), z.toInt())
    }

    override fun toFloat(): Vec3<Float> {
        return Vec3f(x.toFloat(), y.toFloat(), z.toFloat())
    }

    override fun toDouble(): Vec3<Double> {
        return Vec3d(x.toDouble(), y.toDouble(), z.toDouble())
    }

    override fun size() = 3
    override fun get(index: Int): T {
        return when(index) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw IllegalArgumentException("Invalid index: $index")
        }
    }

    fun xy() = Vec2(x, y)
    fun xz() = Vec2(x, z)
    fun yz() = Vec2(y, z)
}
