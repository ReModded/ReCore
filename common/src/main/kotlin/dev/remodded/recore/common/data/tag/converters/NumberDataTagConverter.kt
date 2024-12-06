package dev.remodded.recore.common.data.tag.converters

import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.DataTagConverter
import dev.remodded.recore.api.data.tag.NumericDataTag
import dev.remodded.recore.api.data.tag.StringDataTag

class NumberDataTagConverter : DataTagConverter<Number> {
    override fun from(tag: DataTag): Number? {
        return when (tag) {
            is NumericDataTag -> tag.getValue()
            is StringDataTag -> parse(tag.getValue())
            else -> null
        }
    }

    private fun parse(value: String): Number? {
        return when {
            value.contains(".") -> value.toDouble()
            value.contains("e") || value.contains("E") -> value.toDouble()
            else -> value.toLong()
        }
    }

    override fun to(value: Number) = DataTag.from(value)
}
