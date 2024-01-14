package dev.remodded.recore.sponge_api7;

import com.google.inject.Inject;
import com.google.inject.Injector;
import dev.remodded.recore.api.lib.LibraryLoader;
import dev.remodded.recore.common.Constants;
import dev.remodded.recore.common.lib.DefaultLibraryLoader;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;

@Plugin(
        id = Constants.ID,
        name = Constants.NAME,
        url = Constants.URL,
        description = Constants.DESCRIPTION,
        version = Constants.VERSION,
        authors = Constants.AUTHOR
)
public class ReCoreSponge {

    private final ArrayList<String> dependencies = new ArrayList<String>(Collections.singletonList(
            "org.jetbrains.kotlin:kotlin-stdlib:1.9.21"
    ));
    @Inject
    Injector injector;
    private Logger logger = LoggerFactory.getLogger("ReCoreSpongeBootstrapper");
    private LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, getClass().getClassLoader());
    private ReCoreSpongePlugin plugin;

    @Listener
    public void onServerStart(GameConstructionEvent event) {
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
        plugin = new ReCoreSpongePlugin(libraryLoader);
    }
}
