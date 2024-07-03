package dev.remodded.recore.common.plugins

import dev.remodded.recore.api.plugins.PluginCommon
import dev.remodded.recore.api.plugins.PluginsManager
import dev.remodded.recore.api.plugins.ReCorePlugin

class CommonPluginsManager : PluginsManager {

    val plugins = mutableMapOf<String, ReCorePlugin>()

    override fun <T : PluginCommon> registerPlugin(plugin: ReCorePlugin, commonClass: Class<T>) {
        plugins[plugin.getPluginInfo().name] = plugin

        commonClass.getConstructor().newInstance()
    }

    override fun getPlugin(pluginName: String): ReCorePlugin? {
        return plugins[pluginName]
    }

    override fun getPlugins(): Collection<ReCorePlugin> {
        return plugins.values
    }
}
