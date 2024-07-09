package dev.remodded.recore.common.plugins

import dev.remodded.recore.api.plugins.PluginCommon
import dev.remodded.recore.api.plugins.PluginsManager
import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.utils.getCtorAccess
import java.lang.reflect.Constructor

class CommonPluginsManager : PluginsManager {

    private val plugins = mutableMapOf<String, PluginContainer>()

    override fun <T : PluginCommon> registerPlugin(plugin: ReCorePlugin, commonClass: Class<T>) {
        try {
            var platformedCtor: Constructor<T>? = null

            for (ctor in commonClass.constructors) {
                if (ctor.parameterCount == 1 && ctor.parameterTypes[0].isAssignableFrom(plugin.javaClass)) {
                    @Suppress("UNCHECKED_CAST")
                    platformedCtor = ctor as Constructor<T>
                    break
                }
            }

            if (platformedCtor == null)
                platformedCtor = commonClass.getCtorAccess()

            val instance = platformedCtor.newInstance(plugin)
            plugins[plugin.getPluginInfo().id] = PluginContainer(plugin, instance)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Common class ${commonClass.name} for plugin ${plugin.getPluginInfo().name} is not valid.\nCommon class is required to have ctor that accepts ${plugin.javaClass} or default ctor.", e)
        }
    }

    override fun <T: ReCorePlugin> getPlugin(pluginId: String): T? {
        @Suppress("UNCHECKED_CAST")
        return plugins[pluginId]?.plugin as T?
    }

    override fun getPlugins(): Collection<ReCorePlugin> {
        return plugins.values.map { it.plugin }
    }

    private data class PluginContainer(
        val plugin: ReCorePlugin,
        val common: PluginCommon
    )
}
