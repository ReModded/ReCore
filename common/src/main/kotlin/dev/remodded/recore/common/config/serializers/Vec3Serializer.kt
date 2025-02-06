package dev.remodded.recore.common.config.serializers

import dev.remodded.recore.api.utils.vec.Vec3
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object Vec3Serializer : TypeSerializer<Vec3<*>> {
    val TYPE = Vec3::class.java

    override fun deserialize(
        type: Type,
        node: ConfigurationNode
    ): Vec3<*> {
        if (type !is ParameterizedType)
            throw SerializationException(type, "Raw types are not supported")

        val elementType = type.actualTypeArguments[0]

        val x = node.node("x").get(elementType) as Number
        val y = node.node("y").get(elementType) as Number
        val z = node.node("z").get(elementType) as Number

        return Vec3(x, y, z)
    }

    override fun serialize(type: Type, obj: Vec3<*>?, node: ConfigurationNode) {
        if (obj == null) {
            node.raw(null)
            return
        }

        node.node("x").set(obj.x)
        node.node("y").set(obj.y)
        node.node("z").set(obj.z)
    }
}
