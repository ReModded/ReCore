package dev.remodded.recore.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.remodded.recore.api.lib.LibraryLoader;
import dev.remodded.recore.api.messaging.MessagingManager;
import dev.remodded.recore.api.plugins.PluginInfo;
import dev.remodded.recore.common.Constants;
import dev.remodded.recore.common.ReCoreImpl;
import dev.remodded.recore.common.ReCorePlatformCommon;
import dev.remodded.recore.common.lib.DefaultDependencies;
import dev.remodded.recore.common.lib.DefaultLibraryLoader;
import dev.remodded.recore.velocity.messaging.channel.VelocityChannelMessagingManager;
import kotlin.Unit;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@Plugin(id = Constants.ID)
public class ReCoreVelocity implements ReCorePlatformCommon {
    private final Logger logger = LoggerFactory.getLogger("ReCoreVelocityBootstrapper");
    private final LibraryLoader libraryLoader = new DefaultLibraryLoader(logger, getClass().getClassLoader());

    @Inject
    private ProxyServer proxy;

    @Inject
    @DataDirectory
    private Path dataFolder;

    public static ReCoreVelocity INSTANCE;

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

        var server = new VelocityServer(proxy, libraryLoader, dataFolder.getParent());

        ReCoreImpl.init(server, this, () -> Unit.INSTANCE);
    }

    public ProxyServer getProxy() {
        return proxy;
    }

    @Override
    public @NotNull MessagingManager createChannelMessagingManager() {
        return new VelocityChannelMessagingManager();
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
}
