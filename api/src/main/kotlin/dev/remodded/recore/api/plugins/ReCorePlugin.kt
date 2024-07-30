package dev.remodded.recore.api.plugins

import org.slf4j.Logger

interface ReCorePlugin {

    val logger: Logger?

    fun getPluginInfo(): PluginInfo

    fun hasMigrationSupport(): Boolean {
        return false
    }
}
