package dev.remodded.recore.api.plugins

interface PluginsManager {

    fun <T: PluginCommon> registerPlugin(plugin: ReCorePlugin, commonClass: Class<T>)

    fun getPlugin(pluginName: String): ReCorePlugin?

    fun getPlugins(): Collection<ReCorePlugin>

}
