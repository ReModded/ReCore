package dev.remodded.recore.common.lib;

import dev.remodded.recore.api.lib.LibraryStore;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as a store for libraries used by the ReCore.
 * It keeps a list of library paths and provides methods for adding new libraries and getting all stored libraries paths.
 */
public class ReCoreLibraryStore implements LibraryStore {

    /** A list of paths to the libraries used by the ReCore system. */
    private final List<Path> paths = new ArrayList<>();

    /**
     * Add a new library to the store.
     *
     * @param library A path to the library to be added.
     */
    @Override
    public void addLibrary(@NotNull Path library) {
        this.paths.add(library);
    }

    /**
     * Returns the list of paths to all libraries stored.
     *
     * @return A list of Paths
     */
    public List<Path> getPaths() {
        return this.paths;
    }
}
