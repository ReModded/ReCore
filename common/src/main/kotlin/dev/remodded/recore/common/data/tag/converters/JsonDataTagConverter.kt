package dev.remodded.recore.common.data.tag.converters

import com.google.gson.JsonElement
import dev.remodded.recore.api.data.tag.DataTag
import dev.remodded.recore.api.data.tag.DataTagConverter

class JsonDataTagConverter : DataTagConverter<JsonElement> {
    override fun from(tag: DataTag) = tag.toJson()

    override fun to(value: JsonElement) = DataTag.from(value)
}
