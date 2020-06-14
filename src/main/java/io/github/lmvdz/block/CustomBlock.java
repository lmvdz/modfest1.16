package io.github.lmvdz.block;


import com.google.common.base.CaseFormat;
import io.github.lmvdz.Mod;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * CustomBlock
 */
public class CustomBlock extends Block {

    private String name = "";
    private Identifier identifier;


    public CustomBlock(Block.Settings settings, RenderLayer renderLayer) {
        super(settings);
        BlockRenderLayerMap.INSTANCE.putBlock(this, renderLayer);
    }

    public static void registerCustomBlock(CustomBlock block) {
        setBlockName(block, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, block.getClass().getSimpleName()));
        setIdentifier(block);
        Registry.register(Registry.BLOCK, getIdentifier(block), block);
//        CustomBlock.BLOCKS.put(getIdentifier(block), );
        System.out.println("Registered Block: " + block.getTranslationKey());
    }

    public static boolean isInstanceOf(Block b) {
        return b instanceof CustomBlock;
    }

    public static void setIdentifier(CustomBlock block) {
        block.identifier = new Identifier(Mod.MODID, getBlockName(block));
    }

    public static Identifier getIdentifier(CustomBlock block) {
        return block.identifier;
    }

    public static String getBlockName(CustomBlock block) {
        return block.name;
    }

    public static void setBlockName(CustomBlock block, String name) {
        block.name = name;
    }

    @Override
    public String getTranslationKey() {
        return getBlockName(this).toLowerCase().replaceAll(" ", "_");
    }


}