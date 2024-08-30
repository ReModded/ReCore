package dev.remodded.recore.sponge_api12.mixin.entity;

import dev.remodded.recore.api.entity.GameMode;
import dev.remodded.recore.api.entity.Player;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.server.level.ServerPlayer.class)
public abstract class ServerPlayerMixin implements Player {
    @Shadow @Final public ServerPlayerGameMode gameMode;

    @Override
    public @NotNull GameMode getGamemode() {
        return switch(gameMode.getGameModeForPlayer()) {
            case GameType.SURVIVAL -> GameMode.SURVIVAL;
            case GameType.CREATIVE -> GameMode.CREATIVE;
            case GameType.ADVENTURE -> GameMode.ADVENTURE;
            case GameType.SPECTATOR -> GameMode.SPECTATOR;
        };
    }
}
