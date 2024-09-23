package dev.remodded.recore.common.data.tag

import dev.remodded.recore.api.data.tag.DataTag

abstract class BaseDataTag : DataTag {
    override fun <T : DataTag> cast(type: Class<T>) = tryCast(type) ?: throw createCastException(type)

    override fun <T : DataTag> tryCast(type: Class<T>): T? {
        if (type.isAssignableFrom(javaClass))
            @Suppress("UNCHECKED_CAST")
            return this as T
        return null
    }

    protected fun createCastException(type: Class<*>): ClassCastException {
        return ClassCastException("$javaClass cannot be cast to $type")
    }
}
