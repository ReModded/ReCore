package dev.remodded.recore.velocity.messaging.channel

import com.velocitypowered.api.proxy.ServerConnection
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import com.velocitypowered.api.proxy.server.PingOptions
import com.velocitypowered.api.proxy.server.RegisteredServer
import com.velocitypowered.proxy.VelocityServer
import com.velocitypowered.proxy.protocol.packet.PluginMessagePacket
import com.velocitypowered.proxy.server.VelocityRegisteredServer
import dev.remodded.recore.api.messaging.MessageChannel
import dev.remodded.recore.common.messaging.channel.CommonChannelMessagePacket
import dev.remodded.recore.common.messaging.channel.CommonChannelMessagingManager
import dev.remodded.recore.velocity.ReCoreVelocity
import dev.remodded.recore.velocity.messaging.channel.dummy.DummyVelocityServerConnection
import io.netty.buffer.ByteBufUtil
import java.util.*
import java.util.concurrent.TimeUnit


class VelocityChannelMessagingManager : CommonChannelMessagingManager() {
    companion object {
        private val IDENTIFIER = MinecraftChannelIdentifier.create(CHANNEL_NAMESPACE, CHANNEL_NAME)

        lateinit var INSTANCE: VelocityChannelMessagingManager
    }

    val serversConnections: MutableMap<RegisteredServer, ServerConnection> = WeakHashMap()

    private val serversToConnect = mutableListOf<RegisteredServer>()

    init {
        INSTANCE = this

        val proxy = ReCoreVelocity.INSTANCE.proxy

        proxy.channelRegistrar.register(IDENTIFIER)
        proxy.eventManager.register(ReCoreVelocity.INSTANCE, this)


        serversToConnect.addAll(proxy.allServers)

        proxy.scheduler.buildTask(ReCoreVelocity.INSTANCE) { _ ->
            if (serversToConnect.isEmpty()) return@buildTask

            serversToConnect.toList().forEach { server ->
                if (serversConnections[server] == null) {
                    createServerConnection(server)
                }
            }
        }.repeat(5, TimeUnit.SECONDS).schedule()
    }

    override fun <T> createChannel(channel: String, clazz: Class<T>): MessageChannel<T> {
        return VelocityMessageChannel(this, channel, clazz)
    }

    fun broadcastMessage(packet: CommonChannelMessagePacket): Int {
        val data = VelocityMessagePacket.write(packet)

        var successCount = 0

        serversConnections.values.forEach { connection ->
            if (connection.sendPluginMessage(IDENTIFIER, data))
                successCount++
        }

        return successCount
    }

    fun handleMessage(packetIn: PluginMessagePacket) {
        // handle packet data
        if (packetIn.channel != IDENTIFIER.id) return

        val packet = try{
            val bytes = ByteBufUtil.getBytes(packetIn.content())
            VelocityMessagePacket.read(bytes)
        } catch (e: Exception) {
            println("Error reading message packet")
            e.printStackTrace()
            return
        }

        val channel = channels[packet.channel] as? VelocityMessageChannel ?: return

        channel.handleMessage(packet.message)

        broadcastMessage(packet)
    }

    private fun createServerConnection(server: RegisteredServer) {
        if (server !is VelocityRegisteredServer) {
            println("Server '${server.serverInfo.name}' is not a VelocityRegisteredServer")
            return
        }

        // Ping server to get protocol version
        server.ping(PingOptions.builder().timeout(4, TimeUnit.SECONDS).build()).thenAccept { ping ->
            println("Creating message connection to server: '${server.serverInfo.name}'")

            try {
                serversToConnect.remove(server)

                // Create proxy <-> server connection
                val connection = DummyVelocityServerConnection(
                    server,
                    ReCoreVelocity.INSTANCE.proxy as VelocityServer,
                    ping.version.protocol
                )
                serversConnections[server] = connection
                connection.connect().thenAccept { _ ->
                    if (serversConnections.remove(server) != null)
                        serversToConnect.add(server)
                }.exceptionally {
                    println("Disconnection from server '${server.serverInfo.name}'")
                    if (serversConnections.remove(server) != null)
                        serversToConnect.add(server)
                    return@exceptionally null
                }
            } catch (e: Exception) {
                println("Error creating connection to server '${server.serverInfo.name}'")
                e.printStackTrace()
            }
        }.exceptionally { _ ->
            println("Unable to ping: '${server.serverInfo.name}'")
            return@exceptionally null
        }
    }
}
