package dev.remodded.recore.velocity

import com.velocitypowered.api.network.ProtocolVersion
import com.velocitypowered.api.proxy.ProxyServer
import dev.remodded.recore.api.Server
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.api.world.WorldManager
import dev.remodded.recore.api.world.structure.StructureManager
import dev.remodded.recore.velocity.command.VelocityCommandManager
import dev.remodded.recore.velocity.exception.UnsupportedOnProxyException
import java.nio.file.Path

class VelocityServer(
    val proxy: ProxyServer,
    override val libraryLoader: LibraryLoader,
    dataFolder: Path,
) : Server {

    override val platformInfo = PlatformInfo(
        Platform.VELOCITY,
        proxy.version.name,
        proxy.version.version,
        "[${ProtocolVersion.MINIMUM_VERSION} - ${ProtocolVersion.MAXIMUM_VERSION}]",
        dataFolder,
    )

    override val commandManager = VelocityCommandManager(proxy)
    override val playerManager = VelocityPlayerManager(proxy)
    override val isBehindProxy = false

    override val worldManager: WorldManager get() = throw UnsupportedOnProxyException("Server.worldManager")
    override val structureManager: StructureManager = throw UnsupportedOnProxyException("Server.structureManager")
}
