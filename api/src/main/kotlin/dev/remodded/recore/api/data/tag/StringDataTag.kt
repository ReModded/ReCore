package dev.remodded.recore.api.data.tag

interface StringDataTag : DataTag, CharSequence {
    fun getValue(): String

    fun putValue(value: String): String
}
