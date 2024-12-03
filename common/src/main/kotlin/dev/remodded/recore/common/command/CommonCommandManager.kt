package dev.remodded.recore.common.command

import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.plugins.ReCorePlugin

abstract class CommonCommandManager : CommandManager {

    override fun getPermission(plugin: ReCorePlugin, command: String): String {
        return "${plugin.getPluginInfo().id}.command.$command"
    }

}
