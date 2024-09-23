package dev.remodded.recore.api.data.tag

import com.google.gson.JsonPrimitive

interface NumericDataTag : DataTag {
    fun getValue(): Number
    fun <T: Number> getValue(type: Class<T>): T
    fun <T: Number> putValue(value: T): T

    fun getBool(): Boolean
    fun putValue(value: Boolean): Boolean

    override fun toJson(): JsonPrimitive
}

inline fun <reified T: Number> NumericDataTag.getValue() = getValue(T::class.java)
