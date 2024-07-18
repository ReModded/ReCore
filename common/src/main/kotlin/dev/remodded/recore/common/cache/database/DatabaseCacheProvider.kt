package dev.remodded.recore.common.cache.database

import dev.remodded.recore.api.cache.Cache
import dev.remodded.recore.api.cache.CacheType
import dev.remodded.recore.common.cache.CommonCacheProvider

class DatabaseCacheProvider : CommonCacheProvider() {

    private val caches = mutableMapOf<String, DatabaseCache<*>>()

    override fun getType() = CacheType.DATABASE

    @Suppress("UNCHECKED_CAST")
    override fun <T> getCache(name: String, clazz: Class<T>): Cache<T> {
        sanitizeName(name)

        return caches.getOrPut(name) { DatabaseCache(name, clazz) } as DatabaseCache<T>
    }

}
