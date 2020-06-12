package io.github.lmvdz.mixin;

import io.github.lmvdz.CuboidDynamics;
import io.github.lmvdz.DynamicModelPart;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public class ModelPartMixin {

    @Inject(at = @At("HEAD"), cancellable = true, method = "renderCuboids(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V")
    public void onRenderCuboids(MatrixStack.Entry matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
        ci.cancel();
        Matrix4f matrix4f = matrices.getModel();
        Matrix3f matrix3f = matrices.getNormal();

        for (ModelPart.Cuboid cuboid : ((ModelPartAccessor) this).getCuboids()) {
            DynamicModelPart.DynamicPart[] dynamics = ((CuboidDynamics) cuboid).getDynamics();
            ModelPart.Quad[] sides = ((CuboidAccessor) cuboid).getSides();

            for (ModelPart.Quad quad : sides) {
                Vector3f vector3f = quad.direction.copy();
                vector3f.transform(matrix3f);
                float f = vector3f.getX();
                float g = vector3f.getY();
                float h = vector3f.getZ();

                for (int i = 0; i < 4; ++i) {
                    ModelPart.Vertex vertex = quad.vertices[i];
                    Vector4f vector4f = new Vector4f(
                            vertex.pos.getX() / 16.0F,
                            vertex.pos.getY() / 16.0F,
                            vertex.pos.getZ() / 16.0F,
                            1.0F);
                    vector4f.transform(matrix4f);
                    vertexConsumer.vertex(
                            vector4f.getX() + dynamics[DynamicModelPart.DYNAMIC_ENUM.X.ordinal()].apply(true).value,
                            vector4f.getY() + dynamics[DynamicModelPart.DYNAMIC_ENUM.Y.ordinal()].apply(true).value,
                            vector4f.getZ() + dynamics[DynamicModelPart.DYNAMIC_ENUM.Z.ordinal()].apply(true).value,
                            red + dynamics[DynamicModelPart.DYNAMIC_ENUM.RED.ordinal()].apply(true).value,
                            green + dynamics[DynamicModelPart.DYNAMIC_ENUM.GREEN.ordinal()].apply(true).value,
                            blue + dynamics[DynamicModelPart.DYNAMIC_ENUM.BLUE.ordinal()].apply(true).value,
                            alpha + dynamics[DynamicModelPart.DYNAMIC_ENUM.ALPHA.ordinal()].apply(true).value, vertex.u, vertex.v, overlay,
                            (int) (light + dynamics[DynamicModelPart.DYNAMIC_ENUM.LIGHT.ordinal()].apply(true).value), f, g, h);
                }
            }
        }
    }
}
