package dev.remodded.recore.sponge_api12.mixin.command;

import dev.remodded.recore.api.entity.Entity;
import dev.remodded.recore.api.command.source.CommandSender;
import net.minecraft.commands.CommandSource;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(CommandSource.class)
public interface CommandSourceMixin extends CommandSender, Entity {
    @Override
    default void sendMessage(@NotNull String message) {
        ((CommandSource)this).sendSystemMessage(Component.literal(message));
    }
}
