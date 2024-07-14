package dev.remodded.recore.common.connections.redis

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
class RedisConfig {
    @Comment("Redis instance host or ip address")
    val hostname = "localhost"

    @Comment("Port on which redis server is running (default: \"6379\")")
    val port = 6379

    @Comment("Redis username (default: null)")
    val username: String? = null

    @Comment("Redis password")
    val password: String? = null

    @Comment("Redis messages codec (default: BINARY, valid options: \"JSON\", \"BINARY\")")
    val codec: Codec = Codec.BINARY

    enum class Codec {
        JSON, BINARY
    }
}
