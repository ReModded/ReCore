package dev.remodded.recore.common.config

import dev.remodded.recore.api.config.ConfigManager
import io.leangen.geantyref.TypeToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.ConfigurationOptions
import org.spongepowered.configurate.hocon.HoconConfigurationLoader
import org.spongepowered.configurate.serialize.SerializationException
import java.nio.file.Files
import java.nio.file.Path

/**
 * This class is responsible for managing the configurations.
 *
 * @param T The type of the object that will be loaded from the configuration file.
 * @property configDirectory The directory path where the configuration files are located.
 * @property pluginName The name of the plugin whose config we are loading.
 * @param configClass The class of the configuration object that the file content is to be converted to.
 *
 * The config path is resolved from the default server config location. Inside this location,
 * each plugin has its own directory, named after the plugin itself, which holds its config files.
 */
class DefaultConfigManager<T>(
    private val configDirectory: Path,
    private val pluginName: String,
    configClass: Class<T>
) : ConfigManager<T> {

    private val logger: Logger = LoggerFactory.getLogger("ReCore")
    private val configToken: TypeToken<T> = TypeToken.get(configClass)
    private val configProvider: T = configClass.getDeclaredConstructor().newInstance()

    /**
     * Retrieves the configuration of a given `configName`.
     *
     * @param configName The name of the configuration that needs to be loaded.
     * @return The loaded configuration as an object of type `T`. If it fails to load, returns `null`.
     * @throws SerializationException If the configuration cannot be serialized.
     * @throws ConfigurateException If a configuration exception is encountered.
     */
    @Throws(SerializationException::class, ConfigurateException::class)
    override fun getConfig(configName: String): T? {
        val configFilePath = getConfigPath(pluginName, configName)

        val loader = HoconConfigurationLoader.builder()
            .path(configFilePath)
            .build()

        if (!Files.exists(configFilePath)) {
            logger.info("Config $configName doesn't exist - creating new one")
            saveDefaultConfig(loader)
        }

        if (loader.canLoad()) {
            logger.debug("Loading configuration file")
            return loader.load(ConfigurationOptions.defaults()).get(configToken)
        }

        logger.error("Can not load configuration file: $configFilePath")

        return null;
    }

    /**
     * Saves a default configuration.
     *
     * @param loader Instance of `HoconConfigurationLoader` used to save the default configuration.
     * @throws ConfigurateException If a configuration exception is encountered.
     */
    @Throws(ConfigurateException::class)
    private fun saveDefaultConfig(loader: HoconConfigurationLoader) {
        loader.save(loader.createNode().set(configToken, configProvider))
    }

    /**
     * Constructs the full path of the configuration file from the plugin name and config name.
     *
     * @param pluginName Name of the plugin.
     * @param configName Name of the configuration file.
     * @return Full path to the configuration file.
     */
    private fun getConfigPath(pluginName: String, configName: String): Path {
        var configFileName = configName
        if (!configName.endsWith(".conf"))
            configFileName += ".conf"

        return configDirectory.resolve(pluginName).resolve(configFileName)
    }

    /**
     * Saves the configuration with the given name.
     *
     * @param configName The name of the configuration to save.
     * @param config The configuration object to be saved.
     */
    override fun saveConfig(configName: String, config: T) {
        val configFilePath = getConfigPath(pluginName, configName)

        val loader = HoconConfigurationLoader.builder()
            .path(configFilePath)
            .build()

        loader.save(loader.createNode().set(configToken, config))
    }
}
