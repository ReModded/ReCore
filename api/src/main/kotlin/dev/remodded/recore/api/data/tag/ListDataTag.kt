package dev.remodded.recore.api.data.tag

interface ListDataTag<T: DataTag> : DataTag, MutableList<T> {
    fun <T2: DataTag> ofType(type: Class<T2>): ListDataTag<T2>
}

inline fun <reified T2 : DataTag> ListDataTag<*>.ofType() = ofType(T2::class.javaObjectType)
