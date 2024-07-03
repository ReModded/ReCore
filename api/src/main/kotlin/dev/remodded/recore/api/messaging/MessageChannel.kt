package dev.remodded.recore.api.messaging

interface MessageChannel<T> {
    fun sendMessage(message: T): Boolean

    fun registerListener(listener: MessageListener<T>)
}
