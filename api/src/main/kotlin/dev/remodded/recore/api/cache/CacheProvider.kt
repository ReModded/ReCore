package dev.remodded.recore.api.cache

/**
 * Interface representing a cache provider.
 */
interface CacheProvider {
    /**
     * Retrieves the type of the cache represented by the implementing class.
     *
     * @return the CacheType enum representing the type of the cache.
     */
    fun getType(): CacheType

    /**
     * Retrieves the cache object for the cache provider.
     *
     * @param name the name of the cache to retrieve.
     * @param clazz the class of the cache object.
     * @return the Cache object for the cache provider.
     */
    fun <T> getCache(name: String, clazz: Class<T>): Cache<T>


    companion object {
        /**
         * Retrieves the cache object for the cache provider.
         *
         * @param name the name of the cache to retrieve.
         * @return the Cache object for the cache provider.
         */
        inline fun <reified T> CacheProvider.getCache(name: String): Cache<T> {
            return getCache(name, T::class.java)
        }
    }
}
