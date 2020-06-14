package io.github.lmvdz.mixin;

import io.github.lmvdz.render.BlockRenderLayers;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;


@Mixin(RenderLayer.class)
public class RenderLayerMixin {
    @Inject(at = @At("HEAD"), method = "getBlockLayers()Ljava/util/List;", cancellable = true)
    private static void onGetBlockLayers(CallbackInfoReturnable<List<RenderLayer>> cir) {
        cir.setReturnValue(BlockRenderLayers.getBlockLayers());
    }
}
