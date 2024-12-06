package dev.remodded.recore.common.data.tag

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.NumericDataTag

class NumberDataTag<N: Number>(var data: N) : BaseDataTag(), NumericDataTag {

    init {
        data = castNumber(data, data.javaClass)
    }

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
    override fun toJson() = JsonObject().apply {
        add("@type", JsonPrimitive((NumericType.from(getType()) ?: throw IllegalArgumentException("Invalid number!")).ordinal))
        add("value", JsonPrimitive(data))
    }
    override fun toString() = data.toString()

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T: Number> castNumber(value: Number?, type: Class<T>): T {
            if (value == null) throw IllegalArgumentException("Value cannot be null")
            if (type.isInstance(value)) return value as T

            return (NumericType.from(type)?.cast(value) ?: throw UnsupportedOperationException("Unsupported number type: $type")) as T
        }
    }

    enum class NumericType {
        Byte {
            override fun cast(value: Number): Number = value.toByte()
        },
        Short {
            override fun cast(value: Number): Number = value.toShort()
        },
        Int {
            override fun cast(value: Number): Number = value.toInt()
        },
        Long {
            override fun cast(value: Number): Number = value.toLong()
        },
        Float {
            override fun cast(value: Number): Number = value.toFloat()
        },
        Double {
            override fun cast(value: Number): Number = value.toDouble()
        };

        abstract fun cast(value: Number): Number

        companion object {
            fun from(clazz: Class<out Number>): NumericType? {
                return when (clazz) {
                    java.lang.Byte::class.java    -> Byte
                    java.lang.Short::class.java   -> Short
                    java.lang.Integer::class.java -> Int
                    java.lang.Long::class.java    -> Long
                    java.lang.Float::class.java   -> Float
                    java.lang.Double::class.java  -> Double
                    else -> null
                }
            }
        }
    }
}
