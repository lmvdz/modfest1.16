package io.github.lmvdz.blockitem.blockitems;

import io.github.lmvdz.block.blocks.CustomObsidian;
import io.github.lmvdz.blockitem.CustomBlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;


public class CustomObsidianBlockItem extends CustomBlockItem {

    public static CustomObsidianBlockItem CustomObsidianBlockItem;

    public CustomObsidianBlockItem() {
        super(CustomObsidian.CUSTOM_OBSIDIAN, new Item.Settings().rarity(Rarity.EPIC));
        
        if (CustomObsidianBlockItem == null) {
            CustomObsidianBlockItem = this;
        }
    }
    
}