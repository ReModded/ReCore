package dev.remodded.recore.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.remodded.recore.api.lib.LibraryLoader;
import dev.remodded.recore.common.Constants;
import dev.remodded.recore.common.lib.DefaultDependencies;
import dev.remodded.recore.common.lib.DefaultLibraryLoader;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Plugin(
        id = Constants.ID,
        name = Constants.NAME,
        version = Constants.VERSION,
        url = Constants.URL,
        description = Constants.DESCRIPTION,
        authors = {Constants.AUTHOR}
)
public class ReCoreVelocity {
    Logger logger = LoggerFactory.getLogger("ReCoreVelocityBootstrapper");
    LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, getClass().getClassLoader());
    @Inject
    private ProxyServer server;

    private ReCoreVelocityPlugin plugin;

    @Subscribe
    void onProxyInit(ProxyInitializeEvent event) {
        logger.info("Loading libraries");
        DefaultDependencies.getDependencies().forEach(dependency -> libraryLoader.addLibrary(new Dependency(new DefaultArtifact(dependency), null)));

        try {
            libraryLoader.load();
            logger.info("Libraries loaded");
        } catch (Exception e) {
            logger.error("Error while loading dependencies", e);
        }

        plugin = new ReCoreVelocityPlugin(server, this, libraryLoader);
    }

}
