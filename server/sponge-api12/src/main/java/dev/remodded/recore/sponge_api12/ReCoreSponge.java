package dev.remodded.recore.sponge_api12;

import com.google.inject.Inject;
import dev.remodded.recore.api.lib.LibraryLoader;
import dev.remodded.recore.api.plugins.PluginInfo;
import dev.remodded.recore.api.plugins.ReCorePlugin;
import dev.remodded.recore.common.Constants;
import dev.remodded.recore.common.lib.DefaultDependencies;
import dev.remodded.recore.common.lib.DefaultLibraryLoader;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.plugin.PluginContainer;

import java.lang.reflect.Field;

public class ReCoreSponge implements ReCorePlugin {
    private final Logger logger = LoggerFactory.getLogger("ReCoreSpongeBootstrapper");
    private final LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, getClassLoader());

    public static ReCoreSpongePlatform PLATFORM;
    public static ReCoreSponge INSTANCE;

    @Inject
    public PluginContainer plugin;

    public ReCoreSponge() {
        INSTANCE = this;

        logger.info("Loading libraries");
        DefaultDependencies.getDependencies().forEach(dependency -> libraryLoader.addLibrary(new Dependency(new DefaultArtifact(dependency), null)));

        try {
            libraryLoader.load();
            logger.info("Libraries loaded");
        } catch (Exception e) {
            logger.error("Error while loading dependencies", e);
        }
    }

    @Listener(order = Order.PRE)
    public void onServerStart(StartingEngineEvent<Server> event) {
        PLATFORM = new ReCoreSpongePlatform(libraryLoader);
    }

    private ClassLoader getClassLoader() {
        try {
            ClassLoader loader = this.getClass().getClassLoader();

            // Sponge uses a delegating classloader, so we need to get the actual URLClassLoader
            Field field = loader.getClass().getDeclaredField("delegatedClassLoader");
            field.setAccessible(true);
            Object urlClassLoader = field.get(loader);
            return (ClassLoader) urlClassLoader;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get classloader", e);
        }
    }

    @NotNull
    @Override
    public PluginInfo getPluginInfo() {
        return new  PluginInfo(
            Constants.ID,
            Constants.NAME,
            Constants.VERSION,
            this
        );
    }

    @Nullable
    @Override
    public Logger getLogger() {
        return PLATFORM.getLogger();
    }

    @Override
    public boolean hasMigrationSupport() {
        return false;
    }
}
