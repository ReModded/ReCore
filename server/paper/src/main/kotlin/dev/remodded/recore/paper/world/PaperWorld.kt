package dev.remodded.recore.paper.world

import org.bukkit.World

class PaperWorld(
    private val nativeWorld: World
) : dev.remodded.recore.api.world.World {
    override val name: String
        get() = nativeWorld.name
}
