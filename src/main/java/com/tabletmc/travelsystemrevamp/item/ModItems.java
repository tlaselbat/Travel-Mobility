package com.tabletmc.travelsystemrevamp.item;
import net.minecraft.resource.ResourceManager;
import com.tabletmc.travelsystemrevamp.TravelSystemRevampConstants;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.tabletmc.travelsystemrevamp.TravelSystemRevampConstants.MOD_ID;
import static net.minecraft.item.ArmorMaterials.NETHERITE;

public class ModItems {

	public static final Item NETHERITE_HORSE_ARMOR = registerModItems("netherite_horse_armor",
			new AnimalArmorItem(NETHERITE, AnimalArmorItem.Type.EQUESTRIAN, true,
					new Item.Settings().maxCount(1).fireproof()){

		    @Override
            public void registerModels( ResourceManager manager) {
				super.registerModels(manager);
				manager.register(
                        new Identifier(MOD_ID, "item/netherite_horse_armor"),
                        ModItems.NETHERITE_HORSE_ARMOR.getTexture(0));
//				manager.register(
//                        new Identifier(MOD_ID, "item/netherite_horse_armor_overlay"),
//                        ModItems.NETHERITE_HORSE_ARMOR.getTexture(1));
//
			}

			}


	);


	private static void addItemsToItemGroup(FabricItemGroupEntries entries) {
		entries.add(NETHERITE_HORSE_ARMOR);
	}
	private static Item registerModItems(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
	}
	public static void registerModItems() {
				ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR)
				.register(ModItems::addItemsToItemGroup);
	}


//public static ResourceLocation asResource(String path) {
//	return new ResourceLocation(MOD_ID, path);
//}
}

