package dev.remodded.recore.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
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

import java.nio.file.Path;

@Plugin(
        id = Constants.ID,
        name = Constants.NAME,
        version = Constants.VERSION,
        url = Constants.URL,
        description = Constants.DESCRIPTION,
        authors = {Constants.AUTHOR}
)
public class ReCoreVelocity implements ReCorePlugin {
    private final Logger logger = LoggerFactory.getLogger("ReCoreVelocityBootstrapper");
    private final LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, getClass().getClassLoader());

    @Inject
    private ProxyServer proxy;

    @Inject
    @DataDirectory
    private Path dataFolder;

    public static ReCoreVelocity INSTANCE;
    public static ReCoreVelocityPlatform PLATFORM;

    @Subscribe
    void onProxyInit(ProxyInitializeEvent event) {
        INSTANCE = this;
        logger.info("Loading libraries");
        DefaultDependencies.getDependencies().forEach(dependency -> libraryLoader.addLibrary(new Dependency(new DefaultArtifact(dependency), null)));

        try {
            libraryLoader.load();
            logger.info("Libraries loaded");
        } catch (Exception e) {
            logger.error("Error while loading dependencies", e);
        }

        PLATFORM = new ReCoreVelocityPlatform(proxy, libraryLoader, dataFolder.getParent());
    }

    public ProxyServer getProxy() {
        return proxy;
    }

    @NotNull
    @Override
    public PluginInfo getPluginInfo() {
        return new PluginInfo(
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
