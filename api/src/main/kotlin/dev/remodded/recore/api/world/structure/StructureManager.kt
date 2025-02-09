package dev.remodded.recore.api.world.structure

import java.io.File
import java.io.IOException

interface StructureManager {

    /**
     * Creates a new empty structure.
     *
     * @return an empty structure.
     */
    fun createStructure(): Structure

    /**
     * Reads a Structure from disk.
     *
     * @param file The file of the structure
     * @return The read structure
     * @throws IOException when the given file can not be read from
     */
    @Throws(IOException::class)
    fun loadStructure(file: File): Structure

    /**
     * Save a structure to a file. This will overwrite a file if it already
     * exists.
     *
     * @param file the target to save to.
     * @param structure the Structure to save.
     * @throws IOException when the given file can not be written to.
     */
    @Throws(IOException::class)
    fun saveStructure(file: File, structure: Structure)
}
