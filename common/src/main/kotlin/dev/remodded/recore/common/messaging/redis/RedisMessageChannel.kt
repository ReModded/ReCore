package dev.remodded.recore.common.messaging.redis

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.api.messaging.MessageListener
import org.redisson.api.RTopic


class RedisMessageChannel<T>(private val topic: RTopic, private val clazz: Class<T>) : MessageChannel<T> {

    override fun sendMessage(message: T): Boolean {
        topic.publish(message)
        return true
    }

    override fun registerListener(listener: MessageListener<T>) {
        topic.addListener(clazz) { _, message ->
            listener.onMessage(message)
        }
    }

}
