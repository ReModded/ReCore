package dev.remodded.recore.common

object PluginInfo : dev.remodded.recore.api.PluginInfo {
    override val id: String
        get() = Constants.ID
    override val name: String
        get() = Constants.NAME
    override val version: String
        get() = Constants.VERSION

    override var mainInstance: Any = this
}
