package dev.remodded.recore.api.lib;

import org.eclipse.aether.graph.Dependency;

/**
 * The LibraryLoader interface provides a way to manage
 * the loading of libraries in the application. It includes
 * methods for adding libraries, adding repositories,
 * and to trigger loading process of these libraries.
 */
public interface LibraryLoader {

    /**
     * Adds a new library to the loader.
     *
     * @param dependency The dependency to be added to the library loader.
     */
    void addLibrary(Dependency dependency);

    /**
     * Adds a new repository from where libraries can be fetched.
     *
     * @param id The ID of the repository.
     * @param type The type of the repository.
     * @param url The URL of the repository.
     */
    void addRepository(String id, String type, String url);

    /**
     * Initiates the loading of the libraries added to the loader.
     */
    void load();
}
