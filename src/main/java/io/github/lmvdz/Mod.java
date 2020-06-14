package io.github.lmvdz;

import io.github.lmvdz.block.CustomBlock;
import io.github.lmvdz.block.blocks.CustomObsidian;
import io.github.lmvdz.blockitem.CustomBlockItem;
import io.github.lmvdz.blockitem.blockitems.CustomObsidianBlockItem;
import io.github.lmvdz.render.BlockRenderLayers;
import ladysnake.satin.api.event.EntitiesPreRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderProgram;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;


public class Mod implements ModInitializer {

	public static final String MODID = "destabilize";

	public static final ManagedShaderProgram blockShaderProgram = ShaderEffectManager.getInstance().manageProgram(new Identifier(MODID, "solid"));
	public static final Uniform1f uniformSTime = blockShaderProgram.findUniform1f("STime");
	private static int ticks;
	public static RenderLayer customBlockRenderLayer = blockShaderProgram.getRenderLayer(RenderLayer.getSolid());

	static {
		BlockRenderLayers.register(new Identifier(MODID, "solid"), customBlockRenderLayer);
	}

	@Override
	public void onInitialize() {

		ClientTickCallback.EVENT.register(client -> ticks++);
		EntitiesPreRenderCallback.EVENT.register((camera, frustum, tickDelta) -> uniformSTime.set((ticks + tickDelta) * 0.05f));
		CustomBlock.registerCustomBlock(new CustomObsidian());
		CustomBlockItem.registerBlockItem(new CustomObsidianBlockItem());
		System.out.println("destabilize initialized!");
	}
}
