package com.tabletmc.travelsystemrevamp;
import com.tabletmc.travelsystemrevamp.keybinds.RegisterKeybinds;
import com.tabletmc.travelsystemrevamp.net.RegisterClientTickEvents;
import com.tabletmc.travelsystemrevamp.net.ClientNetworking;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ClientModInitializer implements net.fabricmc.api.ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetworking.init();
        RegisterClientTickEvents.init();
        RegisterKeybinds.ALL.forEach(KeyBindingHelper::registerKeyBinding);

    }
}
