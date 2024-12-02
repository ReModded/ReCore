package dev.remodded.recore.api.config

import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.serialize.SerializationException

interface ConfigLoader<T> {
    /**
     * Retrieves the configuration of a given `configName`.
     *
     * @param configName The name of the configuration that needs to be loaded.
     * @return The loaded configuration as an object of type `T`. If it fails to load, returns `null`.
     * @throws SerializationException If the configuration cannot be serialized.
     * @throws ConfigurateException If a configuration exception is encountered.
     */
    @Throws(SerializationException::class, ConfigurateException::class)
    fun getConfig(configName: String): T?

    /**
     * Saves the configuration `config` with the given `configName`.
     *
     * @param configName The name of the configuration to be saved.
     * @param config The configuration to be saved.
     */
    fun saveConfig(configName: String, config: T)
}
