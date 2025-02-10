package dev.remodded.recore.common.config.serializers

import dev.remodded.recore.api.world.structure.StructureRotation
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

object StructureRotationSerializer : TypeSerializer<StructureRotation> {
    val TYPE = StructureRotation::class.java

    override fun deserialize(type: Type, node: ConfigurationNode): StructureRotation? {
        if (node.virtual() || node.isNull)
            return null

        val raw = node.rawScalar()
        if (raw == null)
            return null

        if (raw is Number)
            return StructureRotation.fromDegrees(raw.toInt())
        if (raw is String)
            return StructureRotation.valueOf(raw)

        throw SerializationException(node, type, "Unsure how to convert value of type " + node.raw() + " to a StructureRotation")
    }

    override fun serialize(type: Type, obj: StructureRotation?, node: ConfigurationNode) {
        if (obj == null){
            node.raw(null)
            return
        }

        node.raw(obj.degrees)
    }
}
