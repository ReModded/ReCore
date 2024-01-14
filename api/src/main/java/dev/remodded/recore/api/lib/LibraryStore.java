package dev.remodded.recore.api.lib;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * Represents a storage that stores library jars.
 * <p>
 * The library store api allows plugins to register specific dependencies into their runtime classloader <br/>
 * This code comes from <a href="https://github.com/PaperMC/Paper/blob/master/LICENSE.md">Paper</a>
 */
public interface LibraryStore {

    /**
     * Adds the provided library path to this library store.
     *
     * @param library path to the libraries jar file on the disk
     */
    void addLibrary(@NotNull Path library);

}
