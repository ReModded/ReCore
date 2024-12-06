package dev.remodded.recore.api.data.tag

interface NumericDataTag : DataTag {
    fun getValue(): Number
    fun <T: Number> getValue(type: Class<T>): T
    fun <T: Number> putValue(value: T): T

    fun getBool(): Boolean
    fun putValue(value: Boolean): Boolean
}

inline fun <reified T: Number> NumericDataTag.getValue() = getValue(T::class.javaObjectType)
