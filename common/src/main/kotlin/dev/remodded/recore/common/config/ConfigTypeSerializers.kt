package dev.remodded.recore.common.config

import dev.remodded.recore.common.config.serializers.ResourceKeySerializer
import org.spongepowered.configurate.serialize.TypeSerializerCollection

object ConfigTypeSerializers {
    val INSTANCE: TypeSerializerCollection = TypeSerializerCollection.defaults().childBuilder()
        .registerExact(ResourceKeySerializer)
        .build()
}
