package dev.remodded.recore.sponge_api12.mixin.entity;

import dev.remodded.recore.api.entity.Entity;
import dev.remodded.recore.api.world.Location;
import dev.remodded.recore.sponge_api12.world.SpongeLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(net.minecraft.world.entity.Entity.class)
public abstract class EntityMixin implements Entity {
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
    public String getName() {
        return ((net.minecraft.world.entity.Entity)(Object)this).getName().getString();
    }

    @NotNull
    @Override
    public Location getLocation() {
        return new SpongeLocation(this.position, this.xRot, this.yRot, this.level);
    }
}
