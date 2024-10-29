package dev.remodded.recore.common.data.tag.converters

import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.DataTagConverter

class StringDataTagConverter : DataTagConverter<String> {
    override fun from(tag: DataTag) = tag.toString()
    override fun to(value: String) = DataTag.from(value)
}
