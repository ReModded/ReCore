package dev.remodded.recore.api.messaging

interface MessagingManager {
    val type: MessagingChannelType

    fun <T>getChannel(channel: String, clazz: Class<T>): MessageChannel<T>

    companion object {
        @JvmStatic
        inline fun <reified T> MessagingManager.getChannel(channel: String): MessageChannel<T> {
            return getChannel(channel, T::class.java)
        }
    }
}


