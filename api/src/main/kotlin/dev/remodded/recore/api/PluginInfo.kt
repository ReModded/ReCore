package dev.remodded.recore.api

interface PluginInfo {
    val id: String
    val name: String
    val version: String
    
    val mainInstance: Any
}
