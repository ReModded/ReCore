package dev.remodded.recore.api.utils.vec

import kotlin.math.sqrt

abstract class Vec2<T: Number> : Vec<T> {
    abstract var x: T
    abstract var y: T

    companion object {
        inline fun<reified T: Number> from(): Vec2<T> = from(T::class.java)
        fun<T: Number> from(clazz: Class<T>): Vec2<T> {
            @Suppress("UNCHECKED_CAST")
            return when(clazz) {
                Double::class.javaObjectType -> Vec2d()
                Float::class.javaObjectType -> Vec2f()
                Int::class.javaObjectType -> Vec2i()
                else -> throw IllegalArgumentException("Unsupported class $clazz")
            } as Vec2<T>
        }

        fun <T: Number> from(v: T): Vec2<T> = from(v.javaClass).apply {
            this.x = v
            this.y = v
        }

        fun <T: Number> from(x: T, y: T): Vec2<T> {
            return from(x.javaClass).apply {
                this.x = x
                this.y = y
            }
        }

        operator fun <T: Number> invoke(v: T) = from(v)
        operator fun <T: Number> invoke(x: T, y: T) = from(x, y)
    }

    abstract override fun copy(): Vec2<T>

    abstract operator fun plusAssign(other: Vec2<T>)
    abstract operator fun minusAssign(other: Vec2<T>)
    abstract operator fun timesAssign(other: Vec2<T>)
    abstract operator fun divAssign(other: Vec2<T>)

    operator fun plus(other: Vec2<T>): Vec2<T> {
        val result = copy()
        result += other
        return result
    }
    operator fun minus(other: Vec2<T>): Vec2<T> {
        val result = copy()
        result -= other
        return result
    }
    operator fun times(other: Vec2<T>): Vec2<T> {
        val result = copy()
        result *= other
        return result
    }
    operator fun div(other: Vec2<T>): Vec2<T> {
        val result = copy()
        result /= other
        return result
    }

    override operator fun times(scalar: T): Vec2<T> {
        val result = copy()
        result *= scalar
        return result
    }
    override operator fun div(scalar: T): Vec2<T> {
        val result = copy()
        result /= scalar
        return result
    }

    override operator fun timesAssign(scalar: T) {
        this *= Vec2(scalar, scalar)
    }
    override operator fun divAssign(scalar: T) {
        this /= Vec2(scalar, scalar)
    }

    abstract override fun lengthSqr(): T
    override fun length(): Double {
        return sqrt(lengthSqr().toDouble())
    }

    override fun toInt(): Vec2i {
        return Vec2i(x.toInt(), y.toInt())
    }

    override fun toFloat(): Vec2f {
        return Vec2f(x.toFloat(), y.toFloat())
    }

    override fun toDouble(): Vec2d {
        return Vec2d(x.toDouble(), y.toDouble())
    }

    override fun size() = 2
    override fun get(index: Int): T {
        return when(index) {
            0 -> x
            1 -> y
            else -> throw IllegalArgumentException("Invalid index: $index")
        }
    }

    fun vxy(v: T) = Vec3(v, x, y)
    fun xvy(v: T) = Vec3(x, v, y)
    fun xyv(v: T) = Vec3(x, y, v)
}
