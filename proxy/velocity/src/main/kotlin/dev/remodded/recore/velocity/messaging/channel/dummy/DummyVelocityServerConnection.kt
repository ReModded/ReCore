package dev.remodded.recore.velocity.messaging.channel.dummy

import com.velocitypowered.api.network.HandshakeIntent
import com.velocitypowered.api.network.ProtocolVersion
import com.velocitypowered.api.proxy.crypto.IdentifiedKey
import com.velocitypowered.api.util.GameProfile
import com.velocitypowered.proxy.VelocityServer
import com.velocitypowered.proxy.connection.MinecraftConnection
import com.velocitypowered.proxy.connection.backend.VelocityServerConnection
import com.velocitypowered.proxy.connection.client.ConnectedPlayer
import com.velocitypowered.proxy.connection.util.ConnectionRequestResults
import com.velocitypowered.proxy.network.Connections
import com.velocitypowered.proxy.protocol.StateRegistry
import com.velocitypowered.proxy.protocol.packet.HandshakePacket
import com.velocitypowered.proxy.protocol.packet.ServerLoginPacket
import com.velocitypowered.proxy.server.VelocityRegisteredServer
import com.velocitypowered.proxy.util.except.QuietRuntimeException
import dev.remodded.recore.api.utils.getFieldAccess
import dev.remodded.recore.common.messaging.channel.CommonChannelMessagingManager
import dev.remodded.recore.velocity.ReCoreVelocity
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import java.net.InetSocketAddress
import java.util.concurrent.CompletableFuture

class DummyVelocityServerConnection(
    registeredServer: VelocityRegisteredServer,
    server: VelocityServer,
    private val protocolVersion: ProtocolVersion,
) : VelocityServerConnection(registeredServer, null, createDummyPlayer(protocolVersion), server) {
    override fun connect(): CompletableFuture<ConnectionRequestResults.Impl> {
        val result = CompletableFuture<ConnectionRequestResults.Impl>()
        val proxy = ReCoreVelocity.INSTANCE.proxy as VelocityServer

        val clazz = VelocityServerConnection::class.java
        val connectionField = clazz.getFieldAccess("connection")
        val connectionPhaseField = clazz.getFieldAccess("connectionPhase")

        // Note: we use the event loop for the connection the player is on. This reduces context
        // switches.
        proxy.createBootstrap(null)
            .handler(proxy.backendChannelInitializer)
            .connect(server.serverInfo.address) // Connect to the server
            .addListener(ChannelFutureListener { future: ChannelFuture ->
                if (future.isSuccess) {
                    val connection = MinecraftConnection(future.channel(), proxy)
                    connectionField.set(this, connection)
                    future.channel().pipeline().addLast(Connections.HANDLER, connection)

                    // Kick off the connection process
                    if (!connection.setActiveSessionHandler(StateRegistry.HANDSHAKE)) {
                        val handler = DummyConnectionHandler(this, result)
                        connection.setActiveSessionHandler(StateRegistry.HANDSHAKE, handler)
                        connection.addSessionHandler(StateRegistry.LOGIN, handler)
                        connection.addSessionHandler(StateRegistry.CONFIG, handler)
                    } else
                        throw QuietRuntimeException("Connection already has a session handler")

                    // Set the connection phase, which may, for future forge (or whatever), be
                    // determined
                    // at this point already
                    connectionPhaseField.set(this, connection.type.initialBackendPhase)
                    startHandshake()
                } else {
                    // Complete the result immediately. ConnectedPlayer will reset the in-flight
                    // connection.
                    result.completeExceptionally(future.cause())
                }
            })
        return result
    }

    private fun startHandshake() {
        val mc = ensureConnected()

        // Initiate the handshake.
        val playerVhost = server.serverInfo.address.hostString

        val handshake = HandshakePacket()
        handshake.intent = HandshakeIntent.LOGIN
        handshake.protocolVersion = protocolVersion
        handshake.serverAddress = playerVhost
        handshake.port = server.serverInfo.address.port
        mc.delayedWrite(handshake)

        mc.protocolVersion = protocolVersion
        mc.setActiveSessionHandler(StateRegistry.LOGIN)
        mc.delayedWrite(ServerLoginPacket(player.username, player.uniqueId))
        mc.flush()
    }

    override fun toString(): String {
        return "[server dummy connection] ${server.serverInfo.name}"
    }

    companion object {
        private fun createDummyPlayer(protocolVersion: ProtocolVersion): ConnectedPlayer {
            val ctor = ConnectedPlayer::class.java.getDeclaredConstructor(
                VelocityServer::class.java,
                GameProfile::class.java,
                MinecraftConnection::class.java,
                InetSocketAddress::class.java,
                String::class.java,
                Boolean::class.java,
                IdentifiedKey::class.java,
            )
            ctor.isAccessible = true

            val profile = GameProfile(CommonChannelMessagingManager.FAKE_PLAYER_UUID, CommonChannelMessagingManager.FAKE_PLAYER_NAME, listOf())

            val dummyChannel = DummyNettyChannel()


            val dummyConnection = object : MinecraftConnection(dummyChannel, ReCoreVelocity.INSTANCE.proxy as VelocityServer) {
                override fun getProtocolVersion(): ProtocolVersion {
                    return protocolVersion
                }
            }

            val instance = ctor.newInstance(
                ReCoreVelocity.INSTANCE.proxy as VelocityServer,
                profile,
                dummyConnection,
                null,
                "",
                false,
                null
            )

            return instance
        }
    }
}
