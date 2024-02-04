package dev.remodded.recore.velocity

import com.velocitypowered.api.proxy.ProxyServer
import dev.remodded.recore.api.ReCorePlatform
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.Platform
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.common.ReCoreImpl
import java.nio.file.Path

class ReCoreVelocityPlatform(
    private val server: ProxyServer,
    override val libraryLoader: LibraryLoader,
    dataFolder: Path,
) : ReCorePlatform {

    override val platformInfo = PlatformInfo(
        Platform.VELOCITY,
        server.version.name,
        server.version.version,
        "proxy",
        dataFolder,
    )

    init {
        ReCoreImpl.init(this)
    }
}
