package dev.remodded.recore.paper;

import dev.remodded.recore.api.lib.LibraryLoader;
import dev.remodded.recore.common.lib.DefaultDependencies;
import dev.remodded.recore.common.lib.DefaultLibraryLoader;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class ReCoreBootstrapper implements PluginBootstrap {
    private final Logger logger = LoggerFactory.getLogger("ReCorePaperBootstrapper");
    private final LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, getClass().getClassLoader().getParent());

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        logger.info("Loading libraries");
        DefaultDependencies.getDependencies().forEach(dependency -> libraryLoader.addLibrary(new Dependency(new DefaultArtifact(dependency), null)));

        try {
            libraryLoader.load();
            logger.info("Libraries loaded");
        } catch (Exception e) {
            logger.error("Error while loading dependencies", e);
        }
    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        return new ReCorePaperPlatform(libraryLoader);
    }
}
