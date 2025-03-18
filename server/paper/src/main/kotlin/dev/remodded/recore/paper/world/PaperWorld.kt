package dev.remodded.recore.paper.world

import dev.remodded.recore.api.world.Location
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

    // todo: wait for Kotlin 2.2
    // override var spawnLocation: Location by native::spawnLocation.mapped(PaperLocation.Mapper)
    override var spawnLocation: Location
        get() = PaperLocation.Mapper.map(native.spawnLocation)
        set(value) { native.spawnLocation = PaperLocation.Mapper.unmap(value) }

    override fun unload(save: Boolean): Boolean {
        return Bukkit.unloadWorld(native, save)
    }
}

fun World.native() = (this as PaperWorld).native
fun org.bukkit.World.wrap() = PaperWorld(this)
