package dev.remodded.recore.sponge_api12.mixin.world;

import dev.remodded.recore.api.world.World;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.world.level.Level.class)
public class WorldMixin implements World {
    @Shadow @Final private ResourceKey<Level> dimension;

    @NotNull
    @Override
    public String getName() {
        return this.dimension.toString();
    }
}
