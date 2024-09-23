package dev.remodded.recore.common.data.tag

import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.NumericDataTag

class NumberDataTag<N: Number>(var data: N) : BaseDataTag(), NumericDataTag {

    override fun getValue() = data
    override fun <T: Number> getValue(type: Class<T>) =
        castNumber(data, type)

    override fun <T: Number> putValue(value: T): T {
        val oldData = getValue(value.javaClass)
        data = castNumber(value, data.javaClass)
        return oldData
    }

    override fun getBool() = data.toInt() >= 1
    override fun putValue(value: Boolean): Boolean {
        val oldData = getBool()
        data = castNumber(if (value) 1 else 0, data.javaClass)
        return oldData
    }

    override fun getType() = data.javaClass
    override fun toJson() = JsonPrimitive(data)
    override fun toString() = data.toString()

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T: Number> castNumber(value: Number?, type: Class<T>): T {
            if (value == null) throw IllegalArgumentException("Value cannot be null")
            if (type.isInstance(value)) return value as T

            return when (type) {
                java.lang.Byte::class.java -> value.toByte()
                Byte::class.java -> value.toByte()
                java.lang.Short::class.java -> value.toShort()
                Short::class.java -> value.toShort()
                java.lang.Integer::class.java -> value.toInt()
                Int::class.java -> value.toInt()
                java.lang.Long::class.java -> value.toLong()
                Long::class.java -> value.toLong()
                java.lang.Float::class.java -> value.toFloat()
                Float::class.java -> value.toFloat()
                java.lang.Double::class.java -> value.toDouble()
                Double::class.java -> value.toDouble()
                else -> throw UnsupportedOperationException("Unsupported number type: $type")
            } as T
        }
    }
}
