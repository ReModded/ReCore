package dev.remodded.recore.common.data.tag

import com.google.gson.JsonObject
import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.ObjectDataTag

class ObjectDataTag(val data: MutableMap<String, DataTag> = mutableMapOf()) : BaseDataTag(), ObjectDataTag, MutableMap<String, DataTag> by data {
    constructor(data: Collection<Pair<String, DataTag>>) : this(HashMap<String, DataTag>(data.size).apply { this.putAll(data) })

    override fun getType() = Map::class.java

    override fun toJson() = JsonObject().apply {
        data.forEach { (key, value) -> add(key, value.toJson()) }
    }

    override fun toString() = toJson().toString()

    override fun <T : DataTag> get(key: String, type: Class<T>): T {
        val value = tryGet<T>(key, type)
        if (value == null)
            if (containsKey(key))
                throw createCastException(type)
            else
                throw IllegalArgumentException("Key '$key' was not found")
        return value
    }
    override fun <T : DataTag> tryGet(key: String, type: Class<T>) = data[key]?.tryCast(type)

    override fun <T : DataTag> getOrPut(key: String, type: Class<T>, defaultValue: T)
        = getOrPut<T>(key, type) { defaultValue }

    override fun <T : DataTag> getOrPut(key: String, type: Class<T>, defaultValue: () -> T): T {
        return tryGet(key, type) ?: defaultValue().also { put(key, it) }
    }

    companion object {
        fun from(data: Map<String, DataTag>) = ObjectDataTag(if(data is MutableMap) data else HashMap(data))
    }
}