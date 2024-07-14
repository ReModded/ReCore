package dev.remodded.recore.common.connections.redis

import dev.remodded.recore.common.ReCoreImpl
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.codec.JsonJacksonCodec
import org.redisson.codec.Kryo5Codec
import org.redisson.config.Config

object Redis {
    val client: RedissonClient by lazy {
        val cfg = ReCoreImpl.INSTANCE.config.redis

        val config = Config()
        config.codec = when (cfg.codec) {
            RedisConfig.Codec.JSON -> JsonJacksonCodec()
            RedisConfig.Codec.BINARY -> Kryo5Codec()
        }
        val serverConfig = config.useSingleServer()
        serverConfig.address = "redis://${cfg.hostname}:${cfg.port}"
        if (cfg.username != null)
            serverConfig.username = cfg.username
        if (cfg.password != null)
            serverConfig.password = cfg.password

        Redisson.create(config)
    }
}
