package dev.remodded.recore.bungee;

import dev.remodded.recore.api.lib.LibraryLoader;
import dev.remodded.recore.common.lib.DefaultLibraryLoader;
import net.md_5.bungee.api.plugin.Plugin;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;

public class ReCoreBungee extends Plugin {

    private final Logger logger = LoggerFactory.getLogger("ReCoreBungeeBootstrapper");
    private final LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, this.getClass().getClassLoader());

    private final ArrayList<String> dependencies = new ArrayList<String>(Collections.singletonList(
            "org.jetbrains.kotlin:kotlin-stdlib:1.9.21"
    ));

    private ReCoreBungeePlugin plugin;

    @Override
    public void onEnable() {
        logger.info("Loading libraries");
        dependencies.forEach(dependency -> {
            libraryLoader.addLibrary(new Dependency(new DefaultArtifact(dependency), null));
        });

        try {
            libraryLoader.load();
            logger.info("Libraries loaded");
        } catch (Exception e) {
            logger.error("Error while loading dependencies", e);
        }

        plugin = new ReCoreBungeePlugin(this, libraryLoader);
    }
}
