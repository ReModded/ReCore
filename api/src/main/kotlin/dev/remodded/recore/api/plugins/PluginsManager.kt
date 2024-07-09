package dev.remodded.recore.api.plugins

interface PluginsManager {

    fun <T: PluginCommon> registerPlugin(plugin: ReCorePlugin, commonClass: Class<T>)

    fun <T: ReCorePlugin> getPlugin(pluginId: String): T?

    fun getPlugins(): Collection<ReCorePlugin>

}
