package dev.remodded.recore.common.data.tag

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.DataTagConverter
import dev.remodded.recore.api.data.tag.DataTagProvider
import dev.remodded.recore.api.data.tag.registerConverter
import dev.remodded.recore.common.data.tag.converters.BooleanDataTagConverter
import dev.remodded.recore.common.data.tag.converters.JsonDataTagConverter
import dev.remodded.recore.common.data.tag.converters.NumberDataTagConverter
import dev.remodded.recore.common.data.tag.converters.StringDataTagConverter
import dev.remodded.recore.common.data.tag.converters.UUIDDataTagConverter
import java.lang.UnsupportedOperationException
import java.math.BigDecimal
import java.math.BigInteger

class CommonDataTagProvider : DataTagProvider {

    override fun from(value: String) = StringDataTag(value)

    override fun from(value: Boolean) = BooleanDataTag(value)
    override fun from(value: Number) = NumberDataTag(value)

    override fun <T : DataTag> from(value: Map<String, T>) = ObjectDataTag.from(value)

    override fun <T : DataTag> from(value: List<T>) = ListDataTag(value)

    override fun objectTag() = ObjectDataTag()

    override fun <T : DataTag> listTag(size: Int) = ListDataTag<T>(size)
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

    override fun from(value: JsonObject) = ObjectDataTag(value.asMap().map { (key, value) -> key to from(value) })

    override fun from(value: JsonArray) = ListDataTag.from(value, this)


    // Converters
    val converters = mutableMapOf<Class<*>, DataTagConverter<*>>()

    override fun <T : Any> registerConverter(type: Class<T>, converter: DataTagConverter<in T>) {
        if (converters.containsKey(type))
            throw IllegalArgumentException("Converter for type $type already registered")

        converters[type] = converter
    }

    override fun <T: Any> from(value: T): DataTag {
        val type = value::class.java

        @Suppress("UNCHECKED_CAST")
        val converter = converters[value.javaClass] as? DataTagConverter<T> ?:
            throw UnsupportedOperationException("Unsupported value type: $type")

        return converter.to(value)
    }

    override fun <T: Any> value(tag: DataTag, type: Class<T>): T? {
        @Suppress("UNCHECKED_CAST")
        val converter = converters[type] as? DataTagConverter<T> ?:
            throw UnsupportedOperationException("Unsupported value type: $type")

        return converter.from(tag)
    }

    init {
        registerConverter(BooleanDataTagConverter())
        registerConverter(StringDataTagConverter())

        registerConverter(NumberDataTagConverter)
        registerConverter<Byte>(NumberDataTagConverter)
        registerConverter<Short>(NumberDataTagConverter)
        registerConverter<Int>(NumberDataTagConverter)
        registerConverter<Long>(NumberDataTagConverter)
        registerConverter<Float>(NumberDataTagConverter)
        registerConverter<Double>(NumberDataTagConverter)
        registerConverter<BigInteger>(NumberDataTagConverter)
        registerConverter<BigDecimal>(NumberDataTagConverter)

//        registerConverter(ListDataTagConverter())
//        registerConverter(MapDataTagConverter())

        registerConverter(JsonDataTagConverter)
        registerConverter<JsonArray>(JsonDataTagConverter)
        registerConverter<JsonObject>(JsonDataTagConverter)
        registerConverter<JsonPrimitive>(JsonDataTagConverter)

        registerConverter(UUIDDataTagConverter())
    }
}
