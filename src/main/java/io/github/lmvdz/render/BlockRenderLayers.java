package io.github.lmvdz.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.*;

public class BlockRenderLayers {
    private static final Map<Identifier, RenderLayer> minecraftBlockRenderLayers = new HashMap<>();
    private static final Map<Identifier, RenderLayer> blockLayers = new HashMap<Identifier, RenderLayer>();

    static {
        minecraftBlockRenderLayers.put(new Identifier("minecraft", "solid"), RenderLayer.getSolid());
        minecraftBlockRenderLayers.put(new Identifier("minecraft", "cutout-mipped"), RenderLayer.getCutoutMipped());
        minecraftBlockRenderLayers.put(new Identifier("minecraft", "cutout"), RenderLayer.getCutout());
        minecraftBlockRenderLayers.put(new Identifier("minecraft", "translucent"), RenderLayer.getTranslucent());
        minecraftBlockRenderLayers.put(new Identifier("minecraft", "tripwire"), RenderLayer.method_29997());
    }


    public static void register(Identifier id, RenderLayer layer) {
        blockLayers.putIfAbsent(id, layer);
    }

    public static List<RenderLayer> getBlockLayers() {
        ArrayList<RenderLayer> renderLayers = new ArrayList<>(minecraftBlockRenderLayers.values());
        renderLayers.addAll(blockLayers.values());
        return new ArrayList<>(renderLayers);
    }
    public static List<RenderLayer> getCustomBlockLayers() {
       return new ArrayList<>(blockLayers.values());
    }
}
