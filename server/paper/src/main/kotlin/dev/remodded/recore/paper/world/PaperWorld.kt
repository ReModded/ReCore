package dev.remodded.recore.paper.world

import dev.remodded.recore.api.world.World


class PaperWorld(
    val native: org.bukkit.World
) : World {
    override val name: String
        get() = native.name
}

fun World.native() = (this as PaperWorld).native
fun org.bukkit.World.wrap() = PaperWorld(this)
