package dev.remodded.recore.paper.world

import dev.remodded.recore.api.world.World
import org.bukkit.Bukkit
import java.util.*


class PaperWorld(
    val native: org.bukkit.World,
) : World {
    override val id: UUID
        get() = native.uid
    override val name: String
        get() = native.name

    override fun unload(save: Boolean): Boolean {
        return Bukkit.unloadWorld(native, save)
    }
}

fun World.native() = (this as PaperWorld).native
fun org.bukkit.World.wrap() = PaperWorld(this)
