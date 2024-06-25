package dev.remodded.recore.api

import dev.remodded.recore.api.command.CommandManager
import dev.remodded.recore.api.config.ConfigManager
import dev.remodded.recore.api.database.DatabaseProvider

interface ReCore {
    val platform: ReCorePlatform

    val databaseProvider: DatabaseProvider?


    val commandManager: CommandManager


    fun <T>createConfigLoader(pluginName: String, configClass: Class<T>): ConfigManager<T>
}
