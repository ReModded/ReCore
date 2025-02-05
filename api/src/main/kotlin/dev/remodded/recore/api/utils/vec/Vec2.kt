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
                Double::class.javaPrimitiveType -> Vec2d()
                Float::class.javaPrimitiveType -> Vec2f()
                Int::class.javaPrimitiveType -> Vec2i()
                else -> throw IllegalArgumentException("Unsupported class $clazz")
            } as Vec2<T>
        }

        fun <T: Number> from(x: T, y: T): Vec2<T> {
            return from(x.javaClass).apply {
                this.x = x
                this.y = y
            }
        }

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
}
