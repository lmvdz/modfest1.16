package io.github.lmvdz.mixin;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Function;


@Mixin(ModelPart.class)
public interface ModelPartAccessor {
    @Accessor
    ObjectList<ModelPart> getChildren();

    @Accessor
    ObjectList<ModelPart.Cuboid> getCuboids();

    @Accessor
    float getPivotX();

    @Accessor
    float getPivotY();

    @Accessor
    float getPivotZ();

    @Accessor
    float getTextureWidth();

    @Accessor
    float getTextureHeight();

    @Accessor
    int getTextureOffsetU();

    @Accessor
    int getTextureOffsetV();


}
