package dev.remodded.recore.api.lib;

/**
 * Indicates that an exception has occurred while loading a library.
 */
public class LibraryLoadingException extends RuntimeException {

    public LibraryLoadingException(String s, Exception e) {
        super(s, e);
    }
}
