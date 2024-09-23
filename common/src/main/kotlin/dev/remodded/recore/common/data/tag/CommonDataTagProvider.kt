package dev.remodded.recore.common.data.tag

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.DataTagProvider
import java.lang.UnsupportedOperationException

object CommonDataTagProvider : DataTagProvider {

    override fun from(value: String) = StringDataTag(value)

    override fun from(value: Boolean) = BooleanDataTag(value)

    override fun from(value: Byte) = NumberDataTag(value)

    override fun from(value: Short) = NumberDataTag(value)

    override fun from(value: Int) = NumberDataTag(value)

    override fun from(value: Long) = NumberDataTag(value)

    override fun from(value: Float) = NumberDataTag(value)

    override fun from(value: Double) = NumberDataTag(value)

    override fun from(value: Number) = NumberDataTag(value)

    override fun <T : DataTag> from(value: Map<String, T>) = ObjectDataTag.from(value)

    override fun <T : DataTag> from(value: List<T>) = ListDataTag(value)

    override fun objectTag() = ObjectDataTag()

    override fun <T : DataTag> listTag(size: Int) = ListDataTag<T>(size)
    override fun <T> listTag(value: List<T>) = ListDataTag(value.map { from(it) })

    override fun from(value: JsonElement): DataTag = when (value) {
        is JsonObject -> from(value)
        is JsonArray -> from(value)

        is JsonPrimitive -> when {
            value.isBoolean -> from(value.asBoolean)
            value.isNumber -> from(value.asNumber)
            value.isString -> from(value.asString)
            else -> null
        }

        else -> null
    } ?: throw IllegalArgumentException("Unsupported value type: ${value::class.java}")

    override fun from(value: JsonObject) = ObjectDataTag(value.asMap().map { (key, value) -> key!! to from(value!!) })

    override fun from(value: JsonArray) = ListDataTag(value.map { from(it) })

    fun <T> from(value: T): DataTag = when (value!!) {
        is String -> from(value)
        is Boolean -> from(value)
        is Number -> from(value)

        is JsonElement -> from(value)

        is List<*> -> ListDataTag(value.map(::from))
        is Map<*, *> ->
            if (value.keys.all { it is String })
                @Suppress("UNCHECKED_CAST")
                ObjectDataTag.from((value as Map<String, *>).mapValues { from(it.value) })
            else
                null

        else -> null
    } ?: throw UnsupportedOperationException("Unsupported value type: ${value::class.java}")
}
