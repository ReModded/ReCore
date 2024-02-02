package dev.remodded.recore.sponge_api11;

import cpw.mods.modlauncher.TransformingClassLoader;
import dev.remodded.recore.api.lib.LibraryLoader;
import dev.remodded.recore.common.lib.DefaultDependencies;
import dev.remodded.recore.common.lib.DefaultLibraryLoader;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;

import java.lang.reflect.Field;

public class ReCoreSponge {
    private final Logger logger = LoggerFactory.getLogger("ReCoreSpongeBootstrapper");
    private final LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, getClassLoader());

    public static ReCoreSpongePlatform PLATFORM;

    @Listener
    public void onServerStart(StartingEngineEvent<Server> event) {
        logger.info("Loading libraries");
        DefaultDependencies.getDependencies().forEach(dependency -> libraryLoader.addLibrary(new Dependency(new DefaultArtifact(dependency), null)));

        try {
            libraryLoader.load();
            logger.info("Libraries loaded");
        } catch (Exception e) {
            logger.error("Error while loading dependencies", e);
        }
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
}
