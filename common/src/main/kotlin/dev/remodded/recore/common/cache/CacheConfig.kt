package dev.remodded.recore.common.cache

import dev.remodded.recore.api.cache.CacheType
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
class CacheConfig {
    val type: CacheType = CacheType.REDIS
}
