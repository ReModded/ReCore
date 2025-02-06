package dev.remodded.recore.api.utils.vec

interface Vec<T: Number> {
    fun size(): Int
    operator fun get(index: Int): T

    fun copy(): Vec<T>

    fun lengthSqr(): T
    fun length(): Double

    fun toInt(): Vec<Int>
    fun toFloat(): Vec<Float>
    fun toDouble(): Vec<Double>

    operator fun times(scalar: T): Vec<T>
    operator fun div(scalar: T): Vec<T>

    operator fun timesAssign(scalar: T)
    operator fun divAssign(scalar: T)
}
