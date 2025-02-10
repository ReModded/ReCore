package dev.remodded.recore.common.config

import dev.remodded.recore.common.config.serializers.ResourceKeySerializer
import dev.remodded.recore.common.config.serializers.StructureRotationSerializer
import dev.remodded.recore.common.config.serializers.Vec2Serializer
import dev.remodded.recore.common.config.serializers.Vec3Serializer
import org.spongepowered.configurate.serialize.TypeSerializerCollection

object ConfigTypeSerializers {
    val INSTANCE: TypeSerializerCollection = TypeSerializerCollection.defaults().childBuilder()
        .registerExact(ResourceKeySerializer)
        .register(StructureRotationSerializer.TYPE, StructureRotationSerializer)
        .register(Vec2Serializer.TYPE, Vec2Serializer)
        .register(Vec3Serializer.TYPE, Vec3Serializer)
        .build()
}
