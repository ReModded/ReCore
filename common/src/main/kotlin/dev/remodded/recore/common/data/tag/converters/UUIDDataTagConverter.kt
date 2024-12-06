package dev.remodded.recore.common.data.tag.converters

import com.google.gson.JsonObject
import dev.remodded.recore.api.data.tag.*
import dev.remodded.recore.api.utils.tryToUUID
import java.util.*

class UUIDDataTagConverter : DataTagConverter<UUID> {
    override fun from(tag: DataTag): UUID? {
        return when (tag) {
            is ObjectDataTag -> {
                val mostBits = tag.tryGet<NumericDataTag>("mostBits") ?: return null
                val leastBits = tag.tryGet<NumericDataTag>("leastBits") ?: return null
                UUID(mostBits.getValue<Long>(), leastBits.getValue<Long>())
            }
            is StringDataTag -> tag.tryToUUID()
            else -> null
        }
    }

    override fun to(value: UUID) = UUIDDataTag(value)

    class UUIDDataTag(value: UUID) : dev.remodded.recore.common.data.tag.ObjectDataTag() {
        init {
            put("mostBits", DataTag.from(value.mostSignificantBits))
            put("leastBits", DataTag.from(value.leastSignificantBits))
        }

        override fun toJson() = JsonObject().apply {
            addProperty("mostBits", getValue<Long>("mostBits"))
            addProperty("leastBits", getValue<Long>("leastBits"))
        }
    }
}
