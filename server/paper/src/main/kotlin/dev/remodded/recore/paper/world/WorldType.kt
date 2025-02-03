package dev.remodded.recore.paper.world

import dev.remodded.recore.api.world.WorldType


fun WorldType.native() = when (this) {
    WorldType.NORMAL -> org.bukkit.WorldType.NORMAL
    WorldType.FLAT -> org.bukkit.WorldType.FLAT
    WorldType.AMPLIFIED -> org.bukkit.WorldType.AMPLIFIED
    WorldType.LARGE_BIOMES -> org.bukkit.WorldType.LARGE_BIOMES
}

fun org.bukkit.WorldType.wrap() = when (this) {
    org.bukkit.WorldType.NORMAL -> WorldType.NORMAL
    org.bukkit.WorldType.FLAT -> WorldType.FLAT
    org.bukkit.WorldType.AMPLIFIED -> WorldType.AMPLIFIED
    org.bukkit.WorldType.LARGE_BIOMES -> WorldType.LARGE_BIOMES
}
