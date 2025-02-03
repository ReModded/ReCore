package dev.remodded.recore.paper.resources

import dev.remodded.recore.api.resources.ResourceKey
import org.bukkit.NamespacedKey

fun ResourceKey.native() = NamespacedKey(this.namespace, this.path)
fun NamespacedKey.wrap() = ResourceKey(this.namespace, this.key)
