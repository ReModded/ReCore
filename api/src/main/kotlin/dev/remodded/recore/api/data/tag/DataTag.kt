package dev.remodded.recore.api.data.tag

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import dev.remodded.recore.api.ReCore
import dev.remodded.recore.api.service.getLazyService

interface DataTag {
    fun <T: DataTag> cast(type: Class<T>): T
    fun <T: DataTag> tryCast(type: Class<T>): T?

    fun getType(): Class<*>

    fun toJson(): JsonElement

    override fun toString(): String

    companion object : DataTagProvider {
        private val tagProvider: DataTagProvider by ReCore.INSTANCE.serviceProvider.getLazyService()

        @JvmStatic override fun from(value: String) = tagProvider.from(value)

        @JvmStatic override fun from(value: Boolean) = tagProvider.from(value)
        @JvmStatic override fun from(value: Byte) = tagProvider.from(value)
        @JvmStatic override fun from(value: Short) = tagProvider.from(value)
        @JvmStatic override fun from(value: Int) = tagProvider.from(value)
        @JvmStatic override fun from(value: Long) = tagProvider.from(value)
        @JvmStatic override fun from(value: Float) = tagProvider.from(value)
        @JvmStatic override fun from(value: Double) = tagProvider.from(value)
        @JvmStatic override fun from(value: Number) = tagProvider.from(value)

        @JvmStatic override fun <T : DataTag> from(value: Map<String, T>) = tagProvider.from<T>(value)

        @JvmStatic override fun <T: DataTag> from(value: List<T>) = tagProvider.from<T>(value)

        @JvmStatic override fun objectTag() = tagProvider.objectTag()

        @JvmStatic override fun <T: DataTag> listTag(size: Int) = tagProvider.listTag<T>(size)
        @JvmStatic override fun <T> listTag(value: List<T>) = tagProvider.listTag(value)

        @JvmStatic override fun from(value: JsonElement) = tagProvider.from(value)
        @JvmStatic override fun from(value: JsonObject) = tagProvider.from(value)
        @JvmStatic override fun from(value: JsonArray) = tagProvider.from(value)
    }
}

inline fun <reified T: DataTag> DataTag.cast() = cast(T::class.java)
inline fun <reified T: DataTag> DataTag.tryCast() = tryCast(T::class.java)

