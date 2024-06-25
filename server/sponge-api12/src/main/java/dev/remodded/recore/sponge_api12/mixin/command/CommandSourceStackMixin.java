package dev.remodded.recore.sponge_api12.mixin.command;

import dev.remodded.recore.api.command.source.CommandSender;
import dev.remodded.recore.api.command.source.CommandSrcStack;
import dev.remodded.recore.api.entity.Entity;
import dev.remodded.recore.api.world.Location;
import dev.remodded.recore.sponge_api12.world.SpongeLocation;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CommandSourceStack.class)
public abstract class CommandSourceStackMixin implements CommandSrcStack {
    @Shadow @Final private CommandSource source;
    @Shadow @Final private Vec3 worldPosition;
    @Shadow @Final private Vec2 rotation;
    @Shadow @Final @Nullable private ServerLevel level;
    @Shadow @Final @Nullable private net.minecraft.world.entity.Entity entity;

    @NotNull
    @Override
    public CommandSender getSender() {
        return (CommandSender)this.source;
    }

    @Nullable
    @Override
    public Entity getEntity() {
        return this.entity == null ? null : (Entity)this.entity;
    }

    @Nullable
    @Override
    public Location getLocation() {
        if (this.level == null)
            return null;

        return new SpongeLocation(this.worldPosition, this.rotation, this.level);
    }
}
