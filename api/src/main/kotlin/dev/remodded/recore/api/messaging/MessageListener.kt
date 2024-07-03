package dev.remodded.recore.api.messaging

fun interface MessageListener<T> {
    fun onMessage(message: T)
}
