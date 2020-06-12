package io.github.lmvdz.mixin;

import io.github.lmvdz.CuboidDynamics;
import io.github.lmvdz.DynamicModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.lmvdz.DynamicModelPart.DYNAMIC_ENUM_LENGTH;
import static net.minecraft.client.model.ModelPart.*;

@Mixin(Cuboid.class)
public class CuboidMixin implements CuboidDynamics {

   public DynamicModelPart.DynamicPart[] dynamics = new DynamicModelPart.DynamicPart[DYNAMIC_ENUM_LENGTH];

   @Inject(at = @At("RETURN"), method = "<init>*")
   public void onInit(CallbackInfo ci) {
      for (int i = 0; i < DYNAMIC_ENUM_LENGTH; i++) {
         dynamics[i] = new DynamicModelPart.DynamicPart(DynamicModelPart.DYNAMIC_ENUM.values()[i], true);
      }
   }

   @Override
   public DynamicModelPart.DynamicPart[] getDynamics() {
      return dynamics;
   }
}
