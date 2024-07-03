package dev.remodded.recore.common.messaging.redis

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.api.messaging.MessagingChannelType
import dev.remodded.recore.common.config.RedisConnection
import dev.remodded.recore.common.messaging.CommonMessagingManager
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.codec.JsonJacksonCodec
import org.redisson.codec.Kryo5Codec
import org.redisson.config.Config

class RedisMessagingManager(
    cfg: RedisConnection,
) : CommonMessagingManager() {
    override val type = MessagingChannelType.REDIS
    private val client: RedissonClient

    init {
        val config = Config()
        config.codec = when (cfg.codec) {
            RedisConnection.Codec.JSON -> JsonJacksonCodec()
            RedisConnection.Codec.BINARY -> Kryo5Codec()
        }
        val serverConfig = config.useSingleServer()
        serverConfig.address = "redis://${cfg.hostname}:${cfg.port}"
        if (cfg.username != null)
            serverConfig.username = cfg.username
        if (cfg.password != null)
            serverConfig.password = cfg.password

        client = Redisson.create(config)
    }

    override fun <T> createChannel(channel: String, clazz: Class<T>): MessageChannel<T> {
        return RedisMessageChannel(client.getTopic(channel), clazz)
    }
}
