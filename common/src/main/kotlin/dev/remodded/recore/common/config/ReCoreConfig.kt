package dev.remodded.recore.common.config

import dev.remodded.recore.api.messaging.MessagingChannelType
import dev.remodded.recore.common.cache.CacheConfig
import dev.remodded.recore.common.connections.redis.RedisConfig
import dev.remodded.recore.common.database.DatabaseConnectionConfig
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
class ReCoreConfig {

    @Comment(
        """
Specify the messaging service used for communication between plugins. Valid options are:

CHANNELS - use default minecraft messaging channels (no external services required). It requires at least one
player connected to the server for it to work.

REDIS - use redis as messaging channel, it is preferred option. When using redis as messaging service you must 
provide connection details to redis server.

POSTGRES - use postgres as messaging service. When using this option you must provide postgresql connection
details to postgresql database.
    """
    )
    val messagingService: MessagingChannelType = MessagingChannelType.CHANNELS

    @Comment("Database connection details")
    val database = DatabaseConnectionConfig()

    @Comment("Redis connection details")
    val redis = RedisConfig()

    @Comment("Cache configuration")
    val cache = CacheConfig()
}
