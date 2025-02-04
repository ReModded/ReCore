package dev.remodded.recore.common.config

import dev.remodded.recore.api.config.ConfigLoader
import dev.remodded.recore.api.config.EnvProvided
import dev.remodded.recore.common.Constants
import io.leangen.geantyref.TypeToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.hocon.HoconConfigurationLoader
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.serialize.SerializationException
import java.lang.reflect.Field
import java.nio.file.Files
import java.nio.file.Path

/**
 * This class is responsible for loading and saving the configurations.
 *
 * @param T The type of the object that will be loaded from the configuration file.
 * @property configDirectory The directory path where the configuration files are located.
 * @param configClass The class of the configuration object that the file content is to be converted to.
 *
 * The config path is resolved from the default server config location. Inside this location,
 * each plugin has its own directory, named after the plugin itself, which holds its config files.
 */
class DefaultConfigLoader<T : Any>(
    private val configDirectory: Path,
    configClass: Class<T>
) : ConfigLoader<T> {

    companion object {
        inline operator fun <reified T: Any> invoke(configPath: Path): ConfigLoader<T> {
            return DefaultConfigLoader(configPath, T::class.java)
        }
    }

    private val logger: Logger = LoggerFactory.getLogger(Constants.NAME)
    private val configToken: TypeToken<T> = TypeToken.get(configClass)
    private val configProvider: ()->T = { configClass.getDeclaredConstructor().newInstance() }

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
        val configFilePath = getConfigPath(configName)

        val loader = createConfigLoader(configFilePath)

        if (!Files.exists(configFilePath)) {
            logger.info("Config $configName doesn't exist - creating new one")
            saveDefaultConfig(loader)
        }

        if (loader.canLoad()) {
            logger.debug("Loading configuration file")
            val config = loader.load().get(configToken)
            if (config != null)
                loadFromEnv(config)
            return config
        }

        logger.error("Can not load configuration file: $configFilePath")

        return null
    }

    /**
     * Saves a default configuration.
     *
     * @param loader Instance of `HoconConfigurationLoader` used to save the default configuration.
     * @throws ConfigurateException If a configuration exception is encountered.
     */
    @Throws(ConfigurateException::class)
    private fun saveDefaultConfig(loader: HoconConfigurationLoader) {
        loader.save(loader.createNode().set(configToken, configProvider()))
    }

    /**
     * Constructs the full path of the configuration file from the plugin name and config name.
     *
     * @param configName Name of the configuration file.
     * @return Full path to the configuration file.
     */
    private fun getConfigPath(configName: String): Path {
        var configFileName = configName
        if (!configName.endsWith(".conf"))
            configFileName += ".conf"

        return configDirectory.resolve(configFileName)
    }

    /**
     * Saves the configuration with the given name.
     *
     * @param configName The name of the configuration to save.
     * @param config The configuration object to be saved.
     */
    override fun saveConfig(configName: String, config: T) {
        val configFilePath = getConfigPath(configName)

        val loader = createConfigLoader(configFilePath)

        loader.save(loader.createNode().set(configToken, config))
    }

    /**
     * Creates a configuration loader for the given configuration file.
     *
     * @param configFilePath The path to the configuration file.
     * @return The configuration loader.
     */
    private fun createConfigLoader(configFilePath: Path): HoconConfigurationLoader {
        return HoconConfigurationLoader.builder()
            .defaultOptions {
                it.serializers(ConfigTypeSerializers.INSTANCE)
            }
            .path(configFilePath)
            .build()
    }

    private fun loadFromEnv(config: T) {
        val env = System.getenv()

        fun load(instance: Any, field: Field) {
            if (field.type.isAnnotationPresent(ConfigSerializable::class.java)) {
                field.isAccessible = true
                val inst = field.get(instance)
                if (inst != null) {
                    inst::class.java.declaredFields.forEach { load(inst, it) }
                }

                return
            }

            val envName = field.getDeclaredAnnotation(EnvProvided::class.java)?.envName
            if (envName != null) {
                val envValue = env[envName]
                if (envValue != null) {
                    field.isAccessible = true

                    if (field.type.isEnum) {
                        for (enumValue in field.type.enumConstants) {
                            if ((enumValue as Enum<*>).name.equals(envValue, true)) {
                                field.set(config, enumValue)
                                return
                            }
                        }
                        throw IllegalArgumentException("Invalid enum value: $envValue")
                    }

                    when (field.type) {
                        String::class.javaPrimitiveType -> field.set(config, envValue)
                        Int::class.javaPrimitiveType -> field.set(config, envValue.toIntOrNull() ?: 0)
                        Long::class.javaPrimitiveType -> field.set(config, envValue.toLongOrNull() ?: 0)
                        Double::class.javaPrimitiveType -> field.set(config, envValue.toDoubleOrNull() ?: 0.0)
                        Boolean::class.javaPrimitiveType -> field.set(config, envValue.toBoolean() == true)
                        else -> throw IllegalArgumentException("Unsupported EnvProvided config type: ${field.type}")
                    }
                }
            }
        }

        config::class.java.declaredFields.forEach { load(config, it) }
    }
}
