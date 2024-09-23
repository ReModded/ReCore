package dev.remodded.recore.common.data.tag

import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.NumericDataTag

class BooleanDataTag(var data: Boolean) : BaseDataTag(), NumericDataTag {
    override fun getValue() = if (data) 1 else 0

    override fun <T: Number> getValue(type: Class<T>) = NumberDataTag.castNumber(getValue(), type)
    override fun <T: Number> putValue(value: T): T {
        val oldData = getValue(value.javaClass)
        data = value.toInt() != 0
        return oldData
    }

    override fun getBool() = data
    override fun putValue(value: Boolean): Boolean {
        val oldData = data
        data = value
        return oldData
    }

    override fun getType() = Boolean::class.java

    override fun toJson() = JsonPrimitive(data)
    override fun toString() = data.toString()
}
