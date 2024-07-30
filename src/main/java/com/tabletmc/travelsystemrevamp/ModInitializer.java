package com.tabletmc.travelsystemrevamp;

import com.tabletmc.travelsystemrevamp.item.ModItems;
import com.tabletmc.travelsystemrevamp.net.ServerNetworking;
import com.tabletmc.travelsystemrevamp.config.TSRModConfig;
//import com.tabletmc.travelsystemrevamp.item.ModItemGroups;


public class ModInitializer implements net.fabricmc.api.ModInitializer {

	@Override
	public void onInitialize()
	{
		ModItems.registerModItems();
		TSRModConfig.init();
		ServerNetworking.init();

		TravelSystemRevampConstants.LOGGER.info("Travel System Revamp Initialized.");


		}


}

