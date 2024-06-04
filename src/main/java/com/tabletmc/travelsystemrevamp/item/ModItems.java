package com.tabletmc.travelsystemrevamp.item;

import com.tabletmc.travelsystemrevamp.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
	//public static final Item MOUNT_WHISTLE = registerModItems("mount_whistle", new Item(new FabricItemSettings()));

	public static final Item NETHERITE_HORSE_ARMOR = registerModItems("netherite_horse_armor",
			new HorseArmorItem(15, "netherite",
					new FabricItemSettings().maxCount(1).fireproof())
	);



	private static void addItemsToItemGroup(FabricItemGroupEntries entries) {
		entries.add(NETHERITE_HORSE_ARMOR);
	}
	private static Item registerModItems(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(TravelSystemRevamp.MOD_ID, name), item);
	}
	public static void registerModItems() {
		TravelSystemRevamp.LOGGER.info("Elden Horses Initialized.");
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR)
				.register(ModItems::addItemsToItemGroup);
	}

}
