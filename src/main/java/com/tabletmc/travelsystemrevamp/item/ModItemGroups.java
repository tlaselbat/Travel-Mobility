package com.tabletmc.travelsystemrevamp.item;

import com.tabletmc.travelsystemrevamp.TravelSystemRevamp;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MOD_ITEMS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(TravelSystemRevamp.MOD_ID, "netherite"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.netherite"))
            .icon(() -> new ItemStack(ModItems.NETHERITE_HORSE_ARMOR))
            .entries((displayContext, entries) -> {

                entries.add(ModItems.NETHERITE_HORSE_ARMOR);
                }).build());
    public static void registerItemGroups(){
        TravelSystemRevamp.LOGGER.info("Registering Item Groups");
    }
}

