package dev.remodded.recore.sponge_api12.mixin.entity;

import dev.remodded.recore.api.entity.Entity;
import dev.remodded.recore.api.world.Location;
import dev.remodded.recore.sponge_api12.world.SpongeLocation;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(net.minecraft.world.entity.Entity.class)
public abstract class EntityMixin implements Entity, CommandSource {
    @Shadow protected UUID uuid;

    @Shadow private Vec3 position;

    @Shadow private float yRot;

    @Shadow private Level level;

    @Shadow private float xRot;

    @NotNull
    @Override
    public UUID getId() {
        return this.uuid;
    }

    @NotNull
    @Override
    public Component getName() {
        return ((org.spongepowered.api.entity.Entity)this).displayName().get();
    }

    @NotNull
    @Override
    public Location getLocation() {
        return new SpongeLocation(this.position, this.xRot, this.yRot, this.level);
    }
}
