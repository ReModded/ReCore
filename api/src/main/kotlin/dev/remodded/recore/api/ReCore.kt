package dev.remodded.recore.api

import dev.remodded.recore.api.config.ConfigManager
import dev.remodded.recore.api.database.DatabaseProvider

interface ReCore {
    fun getPlatform(): ReCorePlatform

    fun getDatabaseProvider(): DatabaseProvider

    fun <T>getConfigLoader(pluginName: String, configClass: Class<T>): ConfigManager<T>
}