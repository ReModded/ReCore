package dev.remodded.recore.common.cache.redis

import dev.remodded.recore.api.cache.Cache
import dev.remodded.recore.common.connections.redis.Redis

class RedisCache<T>(override val name: String, override val entryType: Class<T>) : Cache<T>,
    MutableMap<String, T> by Redis.client.getMap(name)
