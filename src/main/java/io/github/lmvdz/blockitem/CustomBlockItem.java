package io.github.lmvdz.blockitem;

import com.google.common.base.CaseFormat;
import io.github.lmvdz.Mod;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * DeliriumBlockItem
 */
public class CustomBlockItem extends BlockItem {

    private String name = "";
    private Identifier identifier;
    public CustomBlockItem(Block block, Settings settings) {
        super(block, settings.group(ItemGroup.MISC));
    }

    public static void registerBlockItem(CustomBlockItem item) {
        setBlockItemName(item, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, item.getClass().getSimpleName()));
        setIdentifier(item);
        Registry.register(Registry.ITEM, getIdentifier(item), item);
        System.out.println("Registered Block Item: " + item.getTranslationKey());
    }
    public static void setIdentifier(CustomBlockItem item) {
        item.identifier = new Identifier(Mod.MODID, getBlockItemName(item));
    }
    public static Identifier getIdentifier(CustomBlockItem item) {
        return item.identifier;
    }
    public static String getBlockItemName(CustomBlockItem item) {
        return item.name;
    }
    public static void setBlockItemName(CustomBlockItem item, String name) {
        item.name = name;
    }

    @Override
    public String getTranslationKey() {
        return getBlockItemName(this).toLowerCase().replaceAll(" ", "_");
    }
}