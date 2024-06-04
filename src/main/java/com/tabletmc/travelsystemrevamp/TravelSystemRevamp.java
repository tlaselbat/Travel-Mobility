package com.tabletmc.travelsystemrevamp;
import com.tabletmc.travelsystemrevamp.TravelSystemRevampClient;
import com.tabletmc.travelsystemrevamp.config.ModConfig;
import com.tabletmc.travelsystemrevamp.interfaces.ServerPlayerExt;
import com.tabletmc.travelsystemrevamp.item.ModItemGroups;
import com.tabletmc.travelsystemrevamp.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TravelSystemRevamp implements ModInitializer {

/*
	public static final String
	public static final String
	public static final String
	public static final String
	public static final String

*/
public static final Identifier tsr_packet_id = new Identifier("tsvHorse", "summon-horse-data");

	public static final String MOD_ID = "travelsystemrevamp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public Identifier identifier;

	@Override
	public void onInitialize()
	{

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModConfig.init();
		
		LOGGER.info("Travel System Revamp Initialized.");
		ServerPlayNetworking.registerGlobalReceiver(tsr_packet_id, (server, player, handler, buf, responseSender) -> {
			if (buf.readString().equals("summon")) {
				((ServerPlayerExt) player).summonHorse(true);
			}
		});
		}


}

