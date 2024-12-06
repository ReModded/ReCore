package dev.remodded.recore.common.data.tag

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.internal.LazilyParsedNumber
import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.DataTagConverter
import dev.remodded.recore.api.data.tag.DataTagProvider
import dev.remodded.recore.api.data.tag.registerConverter
import dev.remodded.recore.common.data.tag.converters.*

class CommonDataTagProvider : DataTagProvider {

    override fun from(value: String) = StringDataTag(value)

    override fun from(value: Number) =
        if (value is LazilyParsedNumber)
            if (value.toByte().toString() == value.toString())
                NumberDataTag(value.toByte())
            else if (value.toShort().toString() == value.toString())
                NumberDataTag(value.toShort())
            else if (value.toInt().toString() == value.toString())
                NumberDataTag(value.toInt())
            else if (value.toLong().toString() == value.toString())
                NumberDataTag(value.toLong())
            else if (value.toFloat().toString() == value.toString())
                NumberDataTag(value.toFloat())
            else if (value.toDouble().toString() == value.toString())
                NumberDataTag(value.toDouble())
            else
                throw IllegalArgumentException("Unsupported lazy number format: $value")
        else
            NumberDataTag(value)

    override fun from(value: Boolean) = BooleanDataTag(value)

    override fun <T : DataTag> from(value: Map<String, T>) = ObjectDataTag.from(value)

    override fun <T : DataTag> from(value: List<T>) = ListDataTag(value)

    override fun objectTag() = ObjectDataTag()

    override fun <T : DataTag> listTag(capacity: Int) = ListDataTag<T>(capacity)
    override fun <T: Any> listTag(value: List<T>) = ListDataTag(value.map { from(it) })

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

    fun from(value: JsonObject) =
        if (value.has("@type"))
            unwrapNumber(value)
        else
            ObjectDataTag(value.asMap().map { (key, value) -> key to from(value) })

    override fun from(value: JsonArray) = ListDataTag.from(value, this)

    private fun unwrapNumber(jsonObject: JsonObject): NumberDataTag<*> {
        val type = jsonObject.get("@type").asInt
        val value = jsonObject.get("value")
        return when (type) {
            0 -> return NumberDataTag(value.asByte)
            1 -> return NumberDataTag(value.asShort)
            2 -> return NumberDataTag(value.asInt)
            3 -> return NumberDataTag(value.asLong)
            4 -> return NumberDataTag(value.asFloat)
            5 -> return NumberDataTag(value.asDouble)
            else -> throw IllegalArgumentException("Invalid number!")
        }
    }

    // Converters
    val converters = mutableMapOf<Class<*>, DataTagConverter<*>>()

    override fun <T : Any> registerConverter(type: Class<T>, converter: DataTagConverter<in T>) {
        if (converters.containsKey(type))
            throw IllegalArgumentException("Converter for type $type already registered")

        converters[type] = converter
    }

    override fun <T: Any> from(value: T): DataTag {
        val type: Class<T> = value::class.javaObjectType as Class<T>
        val converter = getConverter(type) ?: throw UnsupportedOperationException("Unsupported value type: $type")

        return converter.to(value)
    }

    override fun <T: Any> value(tag: DataTag, type: Class<T>): T? {
        val converter = getConverter(type) ?: throw UnsupportedOperationException("Unsupported value type: $type")
        return converter.from(tag)
    }

    private fun <T: Any> getConverter(type: Class<T>): DataTagConverter<T>? {
        val converter = converters[type]

        if (converter == null) {
            for (converter in converters)
                if (converter.key.isAssignableFrom(type)) {
                    converters[type] = converter.value

//                    println("Converter for ${converter.key} satisfies $type")

                    @Suppress("UNCHECKED_CAST")
                    return converter.value as DataTagConverter<T>
                }
        }

        @Suppress("UNCHECKED_CAST")
        return converter as? DataTagConverter<T>
    }

    init {
        registerConverter(BooleanDataTagConverter())
        registerConverter(StringDataTagConverter())
        registerConverter(NumberDataTagConverter())

        registerConverter(DataTagDataTagConverter())
        registerConverter(JsonDataTagConverter())

        registerConverter(UUIDDataTagConverter())
    }
}
