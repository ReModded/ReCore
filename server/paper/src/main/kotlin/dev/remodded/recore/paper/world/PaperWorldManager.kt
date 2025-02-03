package dev.remodded.recore.paper.world

import dev.remodded.recore.api.resources.ResourceKey
import dev.remodded.recore.api.world.World
import dev.remodded.recore.api.world.WorldCreateConfig
import dev.remodded.recore.api.world.WorldManager
import dev.remodded.recore.paper.resources.native
import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import java.util.*

class PaperWorldManager : WorldManager {
    override fun createWorld(config: WorldCreateConfig): World {
        val worldCreator = WorldCreator.ofKey(config.key.native())
        worldCreator.generatorSettings(config.generatorSettings)
        worldCreator.type(config.type.native())
        val world = Bukkit.createWorld(worldCreator)!!
        return world.wrap()
    }

    override fun getWorld(uuid: UUID): World? {
        return Bukkit.getWorld(uuid)?.wrap()
    }

    override fun getWorld(id: ResourceKey): World? {
        return Bukkit.getWorld(id.native())?.wrap()
    }
}
