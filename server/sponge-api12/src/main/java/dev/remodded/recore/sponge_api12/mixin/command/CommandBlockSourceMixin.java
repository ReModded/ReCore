package dev.remodded.recore.sponge_api12.mixin.command;

import dev.remodded.recore.api.command.source.CommandSender;
import net.minecraft.world.level.BaseCommandBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BaseCommandBlock.class)
public abstract class CommandBlockSourceMixin implements CommandSender {
}
