package dev.remodded.recore.common.data.tag

import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.StringDataTag

class StringDataTag(var data: String): BaseDataTag(), StringDataTag, CharSequence by data {
    override fun getValue() = data
    override fun putValue(value: String): String {
        val oldData = data
        data = value
        return oldData
    }

    override fun getType() = String::class.java

    override fun toJson() = JsonPrimitive(data)
    override fun toString() = data
}
