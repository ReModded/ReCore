package dev.remodded.recore.common.connections.redis

import dev.remodded.recore.common.ReCoreImpl
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.codec.Kryo5Codec
import org.redisson.config.Config

object Redis {
    val client: RedissonClient get() {
        if (!::_client.isInitialized)
            throw IllegalStateException("Redis has not been initialized yet")
        return _client
    }

    private lateinit var _client: RedissonClient

    internal fun init() {
        if (::_client.isInitialized)
            return

        val cfg = ReCoreImpl.INSTANCE.config.redis

        if (!cfg.enabled)
            throw RuntimeException("Redis cannot be initialized due to not being enabled.")

        val config = Config()
        config.codec = when (cfg.codec) {
            RedisConfig.Codec.JSON -> GsonCodec()
            RedisConfig.Codec.BINARY -> Kryo5Codec()
        }
        val serverConfig = config.useSingleServer()
        serverConfig.address = "redis://${cfg.hostname}:${cfg.port}"
        if (cfg.username != null)
            serverConfig.username = cfg.username
        if (cfg.password != null)
            serverConfig.password = cfg.password

        _client = Redisson.create(config)
    }
}
