package dev.remodded.recore.api

import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.PlatformInfo

interface Server {
    val platformInfo: PlatformInfo

    val libraryLoader: LibraryLoader

    val commandManager: CommandManager

    val playerManager: PlayerManager
}
