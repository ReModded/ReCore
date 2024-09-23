package dev.remodded.recore.common.data.tag

import com.google.gson.JsonArray
import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.ListDataTag
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

    override fun toJson() = JsonArray(data.size).apply { data.forEach { add(it.toJson()) } }
    override fun toString() = data.toString()
}
