package io.github.lmvdz.mixin;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Function;


@Mixin(Model.class)
public interface ModelAccessor {
    @Accessor
    Function<Identifier, RenderLayer> getLayerFactory();
}
