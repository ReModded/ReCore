package dev.remodded.recore.api

import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.api.world.WorldManager
import dev.remodded.recore.api.world.structure.StructureManager

interface Server {
    val platformInfo: PlatformInfo

    val libraryLoader: LibraryLoader

    val commandManager: CommandManager

    val playerManager: PlayerManager

    val worldManager: WorldManager

    val structureManager: StructureManager

    val isBehindProxy: Boolean
}
