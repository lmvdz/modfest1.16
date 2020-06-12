package io.github.lmvdz.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPart.Cuboid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Cuboid.class)
public interface CuboidAccessor {
    @Accessor
    ModelPart.Quad[] getSides();
}
