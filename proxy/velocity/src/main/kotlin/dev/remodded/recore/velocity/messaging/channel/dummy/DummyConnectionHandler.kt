package dev.remodded.recore.velocity.messaging.channel.dummy

import com.velocitypowered.api.network.ProtocolVersion
import com.velocitypowered.proxy.VelocityServer
import com.velocitypowered.proxy.config.PlayerInfoForwarding
import com.velocitypowered.proxy.connection.MinecraftSessionHandler
import com.velocitypowered.proxy.connection.PlayerDataForwarding
import com.velocitypowered.proxy.connection.backend.VelocityServerConnection
import com.velocitypowered.proxy.connection.util.ConnectionRequestResults
import com.velocitypowered.proxy.protocol.StateRegistry
import com.velocitypowered.proxy.protocol.packet.*
import com.velocitypowered.proxy.protocol.packet.config.FinishedUpdatePacket
import com.velocitypowered.proxy.util.except.QuietRuntimeException
import dev.remodded.recore.velocity.ReCoreVelocity
import dev.remodded.recore.velocity.messaging.channel.VelocityChannelMessagingManager
import io.netty.buffer.Unpooled
import net.kyori.adventure.text.Component
import java.util.concurrent.CompletableFuture

class DummyConnectionHandler(
    private val serverConn: VelocityServerConnection,
    private val resultFuture: CompletableFuture<ConnectionRequestResults.Impl>
) : MinecraftSessionHandler {

    companion object {
        private val MODERN_IP_FORWARDING_FAILURE: Component = Component.translatable("velocity.error.modern-forwarding-failed")
    }

    private val server: VelocityServer = ReCoreVelocity.INSTANCE.proxy as VelocityServer
    private var informationForwarded = false

    override fun handle(packet: KeepAlivePacket): Boolean {
        serverConn.ensureConnected().write(packet)
        return true
    }

    override fun handle(packet: PluginMessagePacket): Boolean {
        VelocityChannelMessagingManager.INSTANCE.handleMessage(packet)
        return true
    }

    override fun beforeHandle(): Boolean {
        if (!serverConn.isActive) {
            // Obsolete connection
            serverConn.disconnect()
            return true
        }
        return false
    }

    override fun handle(packet: LoginPluginMessagePacket): Boolean {
        val mc = serverConn.ensureConnected()
        val configuration = server.configuration
        if (configuration.playerInfoForwardingMode == PlayerInfoForwarding.MODERN && packet.channel == PlayerDataForwarding.CHANNEL) {
            var requestedForwardingVersion = PlayerDataForwarding.MODERN_DEFAULT
            // Check version
            if (packet.content().readableBytes() == 1) {
                requestedForwardingVersion = packet.content().readByte().toInt()
            }
            val player = serverConn.player
            val forwardingData = PlayerDataForwarding.createForwardingData(
                configuration.forwardingSecret,
                "",
                player.protocolVersion,
                player.gameProfile,
                player.identifiedKey,
                requestedForwardingVersion,
            )

            mc.write(LoginPluginResponsePacket(packet.id, true, forwardingData))
            informationForwarded = true
        } else {
            mc.write(LoginPluginResponsePacket(packet.id, false, Unpooled.EMPTY_BUFFER))
        }
        return true
    }

    override fun handle(packet: DisconnectPacket): Boolean {
        resultFuture.complete(ConnectionRequestResults.forDisconnect(packet, serverConn.server))
        serverConn.disconnect()
        return true
    }

    override fun handle(packet: SetCompressionPacket): Boolean {
        serverConn.ensureConnected().setCompressionThreshold(packet.threshold)
        return true
    }

    override fun handle(packet: ServerLoginSuccessPacket): Boolean {
        if (server.configuration.playerInfoForwardingMode == PlayerInfoForwarding.MODERN && !informationForwarded) {
            resultFuture.complete(
                ConnectionRequestResults.forDisconnect(
                    MODERN_IP_FORWARDING_FAILURE,
                    serverConn.server
                )
            )
            serverConn.disconnect()
            return true
        }

        // The player has been logged on to the backend server, but we're not done yet. There could be
        // other problems that could arise before we get a JoinGame packet from the server.

        // Move into the CONFIG phase.
        val smc = serverConn.ensureConnected()
        if (smc.protocolVersion.lessThan(ProtocolVersion.MINECRAFT_1_21)) {
            throw RuntimeException("ReCore doesn't supports versions < 1.21")
        }

        smc.write(LoginAcknowledgedPacket())
        smc.setActiveSessionHandler(StateRegistry.CONFIG)
//            smc.setActiveSessionHandler(
//                StateRegistry.CONFIG,
//                DummyConfigSessionHandler(serverConn, resultFuture)
//            )

        return true
    }

    override fun exception(throwable: Throwable?) {
        resultFuture.completeExceptionally(throwable)
    }

    override fun handle(packet: ClientboundStoreCookiePacket): Boolean {
        throw IllegalStateException("Can only store cookie in CONFIGURATION or PLAY protocol")
    }

    override fun handle(packet: EncryptionRequestPacket): Boolean {
        throw IllegalStateException("Backend server is online-mode!")
    }

    override fun handle(packet: FinishedUpdatePacket): Boolean {
        throw IllegalStateException("Unexpected FinishedUpdatePacket in configuration state")
    }

    override fun disconnected() {
        if (server.configuration.playerInfoForwardingMode == PlayerInfoForwarding.LEGACY) {
            resultFuture.completeExceptionally(
                QuietRuntimeException(
                    """
              The connection to the remote server was unexpectedly closed.
              This is usually because the remote server does not have BungeeCord IP forwarding correctly enabled.
              See https://velocitypowered.com/wiki/users/forwarding/ for instructions on how to configure player info forwarding correctly.
              """.trimIndent()
                )
            )
        } else {
            resultFuture.completeExceptionally(
                QuietRuntimeException("The connection to the remote server was unexpectedly closed.")
            )
        }
    }
}
