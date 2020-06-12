package io.github.lmvdz;

import java.util.function.Function;
import java.util.stream.Collectors;

import io.github.lmvdz.mixin.ModelAccessor;
import io.github.lmvdz.mixin.ModelPartAccessor;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static io.github.lmvdz.DynamicModelPart.defaultSeeds;

/**
 * Give Model the ability to randomize different parts of each cuboid
 */
public class DynamicModel extends Model {

    public SpriteIdentifier sprite;
    public ObjectList<DynamicModelPart> modelParts;
    public ObjectList<DynamicModel> models;

    /**
     * 1 param Constructor required to support Model extension
     *
     * @param layerFactory Function<Identifier, RenderLayer>, which renderLayer this DynamicModel
     *                     with use.
     */
    public DynamicModel(Function<Identifier, RenderLayer> layerFactory, SpriteIdentifier sprite) {
        super(layerFactory);
        modelParts = new ObjectArrayList<>();
        models = new ObjectArrayList<>();
        this.sprite = sprite;
    }

    /**
     * Create a DynamicModel out of a regular Model
     * @param mainModel Model the model to copy into a DynamicModel
     * @return DynamicModel instance, with data copied from a regular Model
     */
    public static DynamicModel from(Model mainModel, ModelPart[] parts, SpriteIdentifier sprite) {
        DynamicModel dynamicModel = new DynamicModel(((ModelAccessor)mainModel).getLayerFactory(), sprite);
        return dynamicModel.withParts(ObjectArrayList.wrap((DynamicModelPart[])ObjectArrayList.wrap(parts).stream().map(part -> {
            return from(dynamicModel, part);
        }).toArray()));
    }


    public static DynamicModelPart from(DynamicModel dynamicModel, ModelPart modelPart) {
        DynamicModelPart dynamicModelPart = new DynamicModelPart(((ModelPartAccessor)modelPart).getTextureWidth(), ((ModelPartAccessor)modelPart).getTextureHeight(), ((ModelPartAccessor)modelPart).getTextureOffsetU(), ((ModelPartAccessor)modelPart).getTextureOffsetV());
        ObjectList<ModelPart.Cuboid> cuboids = ((ModelPartAccessor)modelPart).getCuboids();
        for (int x = 0; x < cuboids.size(); x++ ) {
            dynamicModelPart.cuboids.set(x, new DynamicModelPart.DynamicCuboid(dynamicModelPart, cuboids.get(x)));
        }
        dynamicModelPart.seeds = defaultSeeds(cuboids.size());
        dynamicModelPart.rotation = new float[] {
                ((ModelPartAccessor)modelPart).getPivotX(),
                ((ModelPartAccessor)modelPart).getPivotY(),
                ((ModelPartAccessor)modelPart).getPivotZ()
        };
        dynamicModelPart.layerFactory = ((ModelAccessor)dynamicModel).getLayerFactory();
        dynamicModelPart.with(false, true, 1, true, false, 50, false, false, 0, true, false, 0, false, false, 0);
        return dynamicModelPart;
    }



    /**
     * Attach DynamicModelParts to this instance of DynamicModel
     *
     * @param modelParts list of dynamic model parts
     * @return DynamicModel instance
     */
    public DynamicModel withParts(ObjectList<DynamicModelPart> modelParts) {
        this.modelParts = modelParts;
        return this;
    }

    /**
     * Attach DynamicModels to this instance of DynamicModel
     *
     * @param models ObjectList<DynamicModel>, list of models to DynamicModel to this instance of
     *               DynamicModel
     * @return DynamicModel instance
     */
    public DynamicModel withModels(ObjectList<DynamicModel> models) {
        this.models = models;
        return this;
    }

    /** builds the cuboids */
    public void build() {
        if (modelParts.size() > 0) {
            modelParts.forEach(DynamicModelPart::build);
        }
        if (models.size() > 0) {
            models.forEach(DynamicModel::build);
        }
    }

    /** builds the cuboids using the linked seeds */
    public void buildUsingSeeds() {
        if (modelParts.size() > 0) {
            modelParts.forEach(DynamicModelPart::buildUsingSeeds);
        }
        if (models.size() > 0) {
            models.forEach(DynamicModel::buildUsingSeeds);
        }
    }

    /**
     * rebuilds the cuboids
     */
    public void rebuild() {
        if (modelParts.size() > 0) {
            modelParts.forEach(DynamicModelPart::rebuild);
        }
        if (models.size() > 0) {
            models.forEach(DynamicModel::rebuild);
        }
    }


    /**
     * Forwards to renderDynamic()
     */
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        models.forEach(model -> {
            model.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        });
        modelParts.forEach(modelPart -> {
            modelPart.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        });
    }

    /**
     * Main DynamicModel Render function
     *
     * @param ticked          -- whether or not the tick has ticked a full tick and not a delta tick
     * @param tick            -- the number of ticks
     * @param matrices        -- the matrixStack - same as render()
     * @param vertexConsumers -- the vertexConsumerProvider -- different from render
     * @param light           -- the amount of light for the model
     * @param overlay         -- the overlay of the model
     * @param red             -- R
     * @param green           -- G
     * @param blue            -- B
     * @param alpha           -- A
     */
    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices,
                              VertexConsumerProvider vertexConsumers, int light, int overlay, float red, float green,
                              float blue, float alpha) {
        models.forEach(model -> {
            model.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay, red, green,
                    blue, alpha);
        });
        modelParts.forEach(modelPart -> {
            modelPart.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay, red,
                    green, blue, alpha);
        });
    }

}
