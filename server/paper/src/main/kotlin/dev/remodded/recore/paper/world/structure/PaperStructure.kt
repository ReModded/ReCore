package dev.remodded.recore.paper.world.structure

import dev.remodded.recore.api.utils.vec.Vec3
import dev.remodded.recore.api.world.Location
import dev.remodded.recore.api.world.structure.Structure
import dev.remodded.recore.paper.world.toNative
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.util.BlockVector
import kotlin.random.Random
import kotlin.random.asJavaRandom

class PaperStructure(
    val native: org.bukkit.structure.Structure
) : Structure {
    override val size: Vec3<Int>
        get() = native.size.let { Vec3(it.x.toInt(), it.y.toInt(), it.z.toInt()) }

    override fun place(location: Location) {
        native.place(location.toNative(), true, StructureRotation.NONE, Mirror.NONE, 0, 0.0f, Random.asJavaRandom())
    }

    override fun fill(location: Location, size: Vec3<Int>) {
        native.fill(location.toNative(), BlockVector(size.x, size.y, size.z), true)
    }
}

fun Structure.native() = (this as PaperStructure).native
fun org.bukkit.structure.Structure.wrap() = PaperStructure(this)
