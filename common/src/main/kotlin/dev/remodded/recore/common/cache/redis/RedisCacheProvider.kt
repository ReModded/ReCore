package dev.remodded.recore.common.cache.redis

import dev.remodded.recore.api.cache.Cache
import dev.remodded.recore.api.cache.CacheType
import dev.remodded.recore.common.cache.CommonCacheProvider
import dev.remodded.recore.common.connections.redis.Redis

class RedisCacheProvider : CommonCacheProvider() {

    init {
        Redis.init()
    }

    override fun getType() = CacheType.REDIS

    override fun <T> getCache(name: String, clazz: Class<T>): Cache<T> {
        sanitizeName(name)

        return RedisCache(name, clazz)
    }

}
