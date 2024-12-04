package dev.remodded.recore.api.data.tag

import com.google.gson.JsonArray
import com.google.gson.JsonElement

interface DataTagProvider {
    fun from(value: String): StringDataTag

    fun from(value: Boolean): NumericDataTag
    fun from(value: Number): NumericDataTag

    fun <T: DataTag> from(value: Map<String, T>): ObjectDataTag

    fun <T: DataTag> from(value: List<T>): ListDataTag<T>

    fun objectTag(): ObjectDataTag
    fun <T: DataTag> listTag(size: Int = 0): ListDataTag<T>
    fun <T: Any> listTag(value: List<T>): ListDataTag<DataTag>

    fun from(value: JsonElement): DataTag
    fun from(value: JsonArray): ListDataTag<DataTag>

    // Converters
    fun <T: Any> registerConverter(type: Class<T>, converter: DataTagConverter<in T>)

    fun <T: Any> from(value: T): DataTag
    fun <T: Any> value(tag: DataTag, type: Class<T>): T?
}

inline fun <reified T: Any> DataTagProvider.registerConverter(converter: DataTagConverter<in T>) =
    registerConverter(T::class.java, converter)
