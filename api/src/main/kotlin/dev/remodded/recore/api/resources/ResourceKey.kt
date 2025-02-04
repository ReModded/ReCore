package dev.remodded.recore.api.resources

import dev.remodded.recore.api.plugins.ReCorePlugin

data class ResourceKey(
    val namespace: String,
    val path: String,
) {
    companion object {
        fun minecraft(path: String) =
            ResourceKey("minecraft", path)

        fun of(plugin: ReCorePlugin, path: String) =
            ResourceKey(plugin.getPluginInfo().id, path)

        fun of(namespace: String, path: String) =
            ResourceKey(namespace, path)

        fun fromString(value: String) =
            value.split(":").let {
                if (it.size != 2)
                    throw IllegalArgumentException("Invalid resource key format '$value'")
                ResourceKey(it[0], it[1])
            }
    }

    override fun toString(): String {
        return "$namespace:$path"
    }
}
