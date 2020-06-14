package io.github.lmvdz.block.blocks;

import io.github.lmvdz.Mod;
import io.github.lmvdz.block.CustomBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class CustomObsidian extends CustomBlock {

    public static CustomObsidian CUSTOM_OBSIDIAN;

    public CustomObsidian() {
        super(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).nonOpaque(), Mod.customBlockRenderLayer);
        if (CUSTOM_OBSIDIAN == null) {
            CUSTOM_OBSIDIAN = this;
        }
    }
}
