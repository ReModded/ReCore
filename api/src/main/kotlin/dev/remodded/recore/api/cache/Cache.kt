package dev.remodded.recore.api.cache

/**
 * Represents a cache that stores entries by a unique key.
 *
 * @param T the type of the stored entries
 */
interface Cache<T> : MutableMap<String, T> {
    val name: String
    val entryType: Class<T>
}
