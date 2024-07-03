package dev.remodded.recore.api

import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.messaging.MessagingManager
import dev.remodded.recore.api.platform.PlatformInfo
import dev.remodded.recore.api.plugins.ReCorePlugin

interface ReCorePlatform : ReCorePlugin {
    val platformInfo: PlatformInfo
    val libraryLoader: LibraryLoader
    val commandManager: CommandManager

    fun createChannelMessagingManager(): MessagingManager
}
