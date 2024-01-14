package dev.remodded.recore.api

import dev.remodded.recore.api.lib.LibraryLoader
import dev.remodded.recore.api.platform.PlatformInfo

interface ReCorePlugin {

    fun getPlatformInfo(): PlatformInfo

    fun getLibraryLoader(): LibraryLoader
}
