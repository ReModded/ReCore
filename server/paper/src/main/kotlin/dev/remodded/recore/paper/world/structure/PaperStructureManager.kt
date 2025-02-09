package dev.remodded.recore.paper.world.structure

import dev.remodded.recore.api.world.structure.Structure
import dev.remodded.recore.api.world.structure.StructureManager
import org.bukkit.Bukkit
import java.io.File

class PaperStructureManager : StructureManager {
    override fun createStructure(): Structure {
        return Bukkit.getStructureManager().createStructure().wrap()
    }

    override fun loadStructure(file: File): Structure {
        return Bukkit.getStructureManager().loadStructure(file).wrap()
    }

    override fun saveStructure(file: File, structure: Structure) {
        Bukkit.getStructureManager().saveStructure(file, structure.native())
    }

}
