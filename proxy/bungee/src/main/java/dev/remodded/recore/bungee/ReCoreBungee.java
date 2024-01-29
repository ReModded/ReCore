package dev.remodded.recore.bungee;

import dev.remodded.recore.api.lib.LibraryLoader;
import dev.remodded.recore.common.lib.DefaultDependencies;
import dev.remodded.recore.common.lib.DefaultLibraryLoader;
import net.md_5.bungee.api.plugin.Plugin;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class ReCoreBungee extends Plugin {

    private final Logger logger = LoggerFactory.getLogger("ReCoreBungeeBootstrapper");
    private final LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, this.getClass().getClassLoader());

    public static ReCoreBungeePlatform PLATFORM;

    @Override
    public void onEnable() {
        logger.info("Loading libraries");
        DefaultDependencies.getDependencies().forEach(dependency -> libraryLoader.addLibrary(new Dependency(new DefaultArtifact(dependency), null)));

        try {
            libraryLoader.load();
            logger.info("Libraries loaded");
        } catch (Exception e) {
            logger.error("Error while loading dependencies", e);
        }

        PLATFORM = new ReCoreBungeePlatform(libraryLoader);
    }
}
