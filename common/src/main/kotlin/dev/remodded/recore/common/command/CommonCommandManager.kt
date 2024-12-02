package dev.remodded.recore.common.command

import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.plugins.PluginInfo

abstract class CommonCommandManager : CommandManager {

    override fun getPermission(plugin: PluginInfo, command: String): String {
        return "${plugin.id}.command.$command"
    }

}
