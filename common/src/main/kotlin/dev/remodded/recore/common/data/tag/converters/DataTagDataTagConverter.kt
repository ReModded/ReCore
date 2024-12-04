package dev.remodded.recore.common.data.tag.converters

import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.DataTagConverter

class DataTagDataTagConverter : DataTagConverter<DataTag> {
    override fun from(tag: DataTag) = tag
    override fun to(value: DataTag) = value
}
