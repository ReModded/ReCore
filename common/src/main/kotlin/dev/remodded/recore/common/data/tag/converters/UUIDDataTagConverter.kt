package dev.remodded.recore.common.data.tag.converters

import dev.remodded.recore.api.data.tag.*
import dev.remodded.recore.api.data.tag.DataTagConverter
import dev.remodded.recore.api.utils.tryToUUID
import java.util.UUID

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

    override fun to(value: UUID) = DataTag.objectTag().apply {
        put("mostBits", DataTag.from(value.mostSignificantBits))
        put("leastBits", DataTag.from(value.leastSignificantBits))
    }
}
