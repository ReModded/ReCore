package dev.remodded.recore.sponge_api12.mixin;

import dev.remodded.recore.api.command.source.CommandSender;
import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(DedicatedServer.class)
public abstract class DedicatedServerMixin implements CommandSender {
}
