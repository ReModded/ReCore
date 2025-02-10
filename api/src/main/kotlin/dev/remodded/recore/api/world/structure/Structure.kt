package dev.remodded.recore.api.world.structure

import dev.remodded.recore.api.utils.vec.Vec3
import dev.remodded.recore.api.world.Location

interface Structure {
    /**
     * The size of the structure.
     */
    val size: Vec3<Int>

    /**
     * Places the structure at the given location.
     *
     * @param location The location to place the structure at.
     */
    fun place(location: Location, rotation: StructureRotation = StructureRotation.NONE)

    fun fill(location: Location, size: Vec3<Int>)
}
