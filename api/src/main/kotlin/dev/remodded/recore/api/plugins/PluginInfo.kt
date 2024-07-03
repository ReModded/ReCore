package dev.remodded.recore.api.plugins

data class PluginInfo (
    val id: String,
    val name: String,
    val version: String,

    val mainInstance: ReCorePlugin,
)
