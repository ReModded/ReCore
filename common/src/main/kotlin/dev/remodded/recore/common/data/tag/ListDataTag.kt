package dev.remodded.recore.common.data.tag

import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive
import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.DataTagProvider
import dev.remodded.recore.api.data.tag.ListDataTag
import dev.remodded.recore.api.data.tag.NumericDataTag
import dev.remodded.recore.api.data.tag.cast

class ListDataTag<T: DataTag>(val data: MutableList<T> = arrayListOf()) : BaseDataTag(), ListDataTag<T>, MutableList<T> by data {

    constructor(data: Collection<T>) : this(if (data is MutableList<T>) data else data.toMutableList())
    constructor(capacity: Int) : this(ArrayList(capacity))

    override fun getType() = List::class.java

    override fun <T2 : DataTag> ofType(type: Class<T2>): ListDataTag<T2> {
        if (all(type::isInstance))
            return cast<ListDataTag<T2>>()
        throw ClassCastException("List contains element of different type")
    }

    override fun toJson(): JsonArray =
        if (data.isEmpty())
            JsonArray()
        else if (
            this.first() is NumericDataTag &&
            this.all { it.getType() == this.first().getType() }
        )
            when (this.first().cast<NumericDataTag>().getValue()) {
                is Byte -> JsonArray(data.size + 1).apply {
                    add(DataTagNumericType.Byte.name)
                    data.forEach { add(it.toJson()) }
                }
                is Int -> JsonArray(data.size + 1).apply {
                    add(DataTagNumericType.Int.name)
                    data.forEach { add(it.toJson()) }
                }
                is Long -> JsonArray(data.size + 1).apply {
                    add(DataTagNumericType.Long.name)
                    data.forEach { add(it.toJson()) }
                }
                is Float -> JsonArray(data.size + 1).apply {
                    add(DataTagNumericType.Float.name)
                    data.forEach { add(it.toJson()) }
                }
                is Double -> JsonArray(data.size + 1).apply {
                    add(DataTagNumericType.Double.name)
                    data.forEach { add(it.toJson()) }
                }
                else -> throw IllegalArgumentException("Unknown data type ${data.javaClass.name}")
            }
        else
            JsonArray(data.size).apply { data.forEach { add(it.toJson()) } }

    override fun toString() = data.toString()


    private enum class DataTagNumericType {
        Byte, Int, Long, Float, Double
    }

    companion object {
        fun from(value: JsonArray, provider: DataTagProvider): ListDataTag<DataTag> {
            if (value.isEmpty) return ListDataTag()

            val first = value.get(0)
            if (first is JsonPrimitive && first.isString) {
                val type = DataTagNumericType.values().find { first.asString == it.name }
                if (type != null) {
                    try {
                        return when (type) {
                            DataTagNumericType.Byte   -> ListDataTag(value.asSequence().drop(1).map { provider.from(it.asByte) }.toMutableList())
                            DataTagNumericType.Int    -> ListDataTag(value.asSequence().drop(1).map { provider.from(it.asInt) }.toMutableList())
                            DataTagNumericType.Long   -> ListDataTag(value.asSequence().drop(1).map { provider.from(it.asLong) }.toMutableList())
                            DataTagNumericType.Float  -> ListDataTag(value.asSequence().drop(1).map { provider.from(it.asFloat) }.toMutableList())
                            DataTagNumericType.Double -> ListDataTag(value.asSequence().drop(1).map { provider.from(it.asDouble) }.toMutableList())
                        }
                    } catch (_: UnsupportedOperationException) {}
                }
            }
            return ListDataTag(value.map { provider.from(it) })
        }
    }
}
