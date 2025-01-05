package dev.remodded.recore.common.messaging.redis

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.api.messaging.MessagingChannelType
import dev.remodded.recore.common.connections.redis.Redis
import dev.remodded.recore.common.messaging.CommonMessagingManager

class RedisMessagingManager : CommonMessagingManager() {
    init {
        Redis.init()
    }

    override val type = MessagingChannelType.REDIS

    override fun <T> createChannel(channel: String, clazz: Class<T>): MessageChannel<T> {
        Redis.addClassLoader(clazz.classLoader)
        return RedisMessageChannel(Redis.client.getTopic(channel), clazz)
    }
}
