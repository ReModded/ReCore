package dev.remodded.recore.common.config.serializers

import dev.remodded.recore.api.utils.vec.Vec2
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object Vec2Serializer : TypeSerializer<Vec2<*>> {
    val TYPE = Vec2::class.java

    override fun deserialize(
        type: Type,
        node: ConfigurationNode
    ): Vec2<*> {
        if (type !is ParameterizedType)
            throw SerializationException(type, "Raw types are not supported")

        val elementType = type.actualTypeArguments[0]

        val x = node.node("x").get(elementType) as Number
        val y = node.node("y").get(elementType) as Number

        return Vec2(x, y)
    }

    override fun serialize(type: Type, obj: Vec2<*>?, node: ConfigurationNode) {
        if (obj == null) {
            node.raw(null)
            return
        }

        node.node("x").set(obj.x)
        node.node("y").set(obj.y)
    }
}
