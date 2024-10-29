package dev.remodded.recore.common.data.tag.converters

import dev.remodded.recore.api.data.tag.*
import dev.remodded.recore.api.data.tag.DataTagConverter

class BooleanDataTagConverter : DataTagConverter<Boolean> {
    override fun from(tag: DataTag): Boolean? {
        return when (tag) {
            is NumericDataTag -> tag.getBool()
            is StringDataTag -> tag.getValue().equals("true", ignoreCase = true)
            else -> null
        }
    }

    override fun to(value: Boolean) = DataTag.from(value)
}
