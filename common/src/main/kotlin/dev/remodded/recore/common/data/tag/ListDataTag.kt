package dev.remodded.recore.common.data.tag

import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.*
import dev.remodded.recore.api.data.tag.ListDataTag
import dev.remodded.recore.common.data.tag.NumberDataTag.NumericType

class ListDataTag<T: DataTag>(val data: MutableList<T> = arrayListOf()) : BaseDataTag(), ListDataTag<T>, MutableList<T> by data {

    constructor(data: Collection<T>) : this(data as? MutableList<T> ?: data.toMutableList())
    constructor(capacity: Int) : this(ArrayList(capacity))

    override fun getType() = List::class.java

    override fun <T2 : DataTag> ofType(type: Class<T2>): ListDataTag<T2> {
        if (all(type::isInstance))
            return cast<ListDataTag<T2>>()
        throw ClassCastException("List contains element of different type")
    }

    override fun toJson(): JsonArray {
        if (data.isEmpty())
            return JsonArray()

        val first = first()

        if (
            first is NumericDataTag &&
            this.all { it.getType() == first.getType() }
        ) {
            @Suppress("UNCHECKED_CAST")
            val type = NumericType.from(first.getType() as Class<Number>)
                ?: throw IllegalArgumentException("Unknown data type ${data.javaClass.name}")

            return JsonArray(data.size + 1).apply {
                add("@type=${type.ordinal}")
                data.forEach { tag -> add((tag as NumericDataTag).getValue()) }
            }
        }

        return JsonArray(data.size).apply { data.forEach { add(it.toJson()) } }
    }


    override fun toString() = data.toString()

    companion object {
        fun from(value: JsonArray, provider: DataTagProvider): ListDataTag<DataTag> {
            if (value.isEmpty) return ListDataTag()

            val first = value.get(0)
            if (first is JsonPrimitive && first.isString && first.asString.startsWith("@type=")) {
                val type = NumericType.entries.getOrNull(first.asString.substring("@type=".length).toInt())
                if (type != null) {
                    try {
                        return ListDataTag(value.asSequence().drop(1).map { provider.from(type.cast(it.asNumber)) }.toMutableList())
                    } catch (e: UnsupportedOperationException) {
                        println(e)
                    }
                }
            }
            return ListDataTag(value.map { provider.from(it) })
        }
    }
}
