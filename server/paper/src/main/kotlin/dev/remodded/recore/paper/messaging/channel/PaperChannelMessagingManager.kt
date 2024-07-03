package dev.remodded.recore.paper.messaging.channel

import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.common.messaging.channel.CommonChannelMessagingManager
import dev.remodded.recore.paper.ReCorePaperPlatform
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.messaging.PluginMessageListener
import org.bukkit.plugin.messaging.PluginMessageRecipient


class PaperChannelMessagingManager : CommonChannelMessagingManager(), PluginMessageListener, Listener {

    companion object {
        lateinit var INSTANCE: PaperChannelMessagingManager
    }

    var proxyConnection: PluginMessageRecipient? = null

    init {
        INSTANCE = this

        val plugin = ReCorePaperPlatform.INSTANCE
        val server = plugin.server
        val messenger = server.messenger
        messenger.registerOutgoingPluginChannel(plugin, CHANNEL_ID)
        messenger.registerIncomingPluginChannel(plugin, CHANNEL_ID, this)

        server.pluginManager.registerEvents(this, plugin)
    }

    override fun <T> createChannel(channel: String, clazz: Class<T>): MessageChannel<T> {
        return PaperMessageChannel(channel, clazz)
    }

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != CHANNEL_ID) return

        val packet = PaperMessagePacket.read(message)
        val messageChannel = channels[packet.channel] as? PaperMessageChannel<*> ?: return

        messageChannel.handlePacket(packet)
    }

//    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
//    fun onPlayerMove(ev: PlayerLinksSendEvent) {
//        if (ev.player.name == FAKE_PLAYER_NAME) {
//            proxyConnection = ev.player
//
//            println(Bukkit.getOnlinePlayers())
//        }
//    }
}


