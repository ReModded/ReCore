package dev.remodded.recore.api.data.tag

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

interface DataTagProvider {
    fun from(value: String): StringDataTag

    fun from(value: Boolean): NumericDataTag
    fun from(value: Byte): NumericDataTag
    fun from(value: Short): NumericDataTag
    fun from(value: Int): NumericDataTag
    fun from(value: Long): NumericDataTag
    fun from(value: Float): NumericDataTag
    fun from(value: Double): NumericDataTag
    fun from(value: Number): NumericDataTag

    fun <T: DataTag> from(value: Map<String, T>): ObjectDataTag

    fun <T: DataTag> from(value: List<T>): ListDataTag<T>

    fun objectTag(): ObjectDataTag
    fun <T: DataTag> listTag(size: Int = 0): ListDataTag<T>
    fun <T> listTag(value: List<T>): ListDataTag<DataTag>

    fun from(value: JsonElement): DataTag
    fun from(value: JsonObject): ObjectDataTag
    fun from(value: JsonArray): ListDataTag<DataTag>
}
