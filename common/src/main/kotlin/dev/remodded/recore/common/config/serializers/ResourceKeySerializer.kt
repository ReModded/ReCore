package dev.remodded.recore.common.config.serializers

import dev.remodded.recore.api.resources.ResourceKey
import dev.remodded.recore.api.resources.ResourceKey.Companion.fromString
import org.spongepowered.configurate.serialize.CoercionFailedException
import org.spongepowered.configurate.serialize.ScalarSerializer
import org.spongepowered.configurate.serialize.SerializationException
import java.lang.reflect.Type
import java.util.function.Predicate

object ResourceKeySerializer : ScalarSerializer<ResourceKey>(ResourceKey::class.java) {
    override fun deserialize(type: Type, obj: Any): ResourceKey {
        if (obj is String)
            return try {
                fromString(obj)
            } catch (e: IllegalArgumentException) {
                throw SerializationException(type, e)
            }
        throw CoercionFailedException(type, obj, "ResourceKey")
    }

    override fun serialize(item: ResourceKey, typeSupported: Predicate<Class<*>>) =
        item.toString()
}
