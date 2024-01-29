package dev.remodded.recore.sponge_api11;

import cpw.mods.modlauncher.TransformingClassLoader;
import dev.remodded.recore.api.lib.LibraryLoader;
import dev.remodded.recore.common.lib.DefaultDependencies;
import dev.remodded.recore.common.lib.DefaultLibraryLoader;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;

import java.lang.reflect.Field;
import java.util.Objects;

public class ReCoreSponge {

    private final Logger logger = LoggerFactory.getLogger("ReCoreSpongeBootstrapper");
    private final LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, Objects.requireNonNull(getClassLoader()));

    public static ReCoreSpongePlatform PLATFORM;

    @Listener
    public void onServerStart(LoadedGameEvent event) {
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
            TransformingClassLoader classLoader = (TransformingClassLoader) this.getClass().getClassLoader();

            Class<?> transfromingClassLoaderClass = TransformingClassLoader.class;
            Field field = transfromingClassLoaderClass.getDeclaredField("delegatedClassLoader");
            field.setAccessible(true);
            Object urlClassLoader = field.get(classLoader);
            return (ClassLoader) urlClassLoader;
        } catch (Exception e) {
            return null;
        }
    }
}
