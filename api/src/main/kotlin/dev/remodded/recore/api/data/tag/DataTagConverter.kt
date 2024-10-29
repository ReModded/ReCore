package dev.remodded.recore.api.data.tag

interface DataTagConverter<T>  {
    fun from(tag: DataTag): T?
    fun to(value: T): DataTag
}
