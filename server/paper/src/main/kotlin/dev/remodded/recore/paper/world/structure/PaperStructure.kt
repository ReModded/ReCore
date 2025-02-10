package dev.remodded.recore.paper.world.structure

import dev.remodded.recore.api.utils.vec.Vec3
import dev.remodded.recore.api.world.Location
import dev.remodded.recore.api.world.structure.Structure
import dev.remodded.recore.api.world.structure.StructureRotation
import dev.remodded.recore.paper.world.toNative
import org.bukkit.block.structure.Mirror
import org.bukkit.util.BlockVector
import kotlin.random.Random
import kotlin.random.asJavaRandom

class PaperStructure(
    val native: org.bukkit.structure.Structure
) : Structure {
    override val size: Vec3<Int>
        get() = native.size.let { Vec3(it.x.toInt(), it.y.toInt(), it.z.toInt()) }

    override fun place(location: Location, rotation: StructureRotation) {
        val rotation = when (rotation) {
            StructureRotation.NONE -> org.bukkit.block.structure.StructureRotation.NONE
            StructureRotation.CLOCKWISE_90 -> org.bukkit.block.structure.StructureRotation.CLOCKWISE_90
            StructureRotation.CLOCKWISE_180 -> org.bukkit.block.structure.StructureRotation.CLOCKWISE_180
            StructureRotation.COUNTERCLOCKWISE_90 -> org.bukkit.block.structure.StructureRotation.COUNTERCLOCKWISE_90
            else -> throw IllegalArgumentException("Unknown rotation: $rotation")
        }

        native.place(location.toNative(), true, rotation, Mirror.NONE, 0, 0.0f, Random.asJavaRandom())
    }

    override fun fill(location: Location, size: Vec3<Int>) {
        native.fill(location.toNative(), BlockVector(size.x, size.y, size.z), true)
    }
}

fun Structure.native() = (this as PaperStructure).native
fun org.bukkit.structure.Structure.wrap() = PaperStructure(this)
