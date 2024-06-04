package com.tabletmc.travelsystemrevamp;
import com.tabletmc.travelsystemrevamp.TravelSystemRevamp;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import net.minecraft.network.packet.CustomPayload;

import java.util.UUID;

import static com.tabletmc.travelsystemrevamp.TravelSystemRevamp.tsr_packet_id;

public class TravelSystemRevampClient implements ClientModInitializer {

    private static boolean summonCooldown = false;
    public static KeyBinding horsePlayerInventory;

    static {
        horsePlayerInventory = KeyBindingHelper2.registerKeyBinding(new KeyBinding(
                "text.HorseBuff.keybinding.horsePlayerInventory",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                "text.HorseBuff.keybinding.category"
        ));
    }

    // porting this over to other client init!!!!!!!! //
public static class KeyBindingHelper {
    public static KeyBinding registerKeyBinding(KeyBinding keyBinding1) {
        return keyBinding1;
    }
};
private static class KeyBindingHelper2 {
    public static KeyBinding registerKeyBinding(KeyBinding keyBinding2) {
        return keyBinding2;
    }
};

    public static void createKeybinds() {
        // Summon Horse Keybind
        KeyBinding keyBinding1;
        keyBinding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.travelsystemrevamp.summerhouse",
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                "travelsystemrevamp.category"
        ));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (keyBinding1.isPressed())
                if (!summonCooldown) {
                    if (client.player != null && client.player.hasVehicle()) {
                        client.player.sendMessage(Text.of("Dismount to summon!"), true);
                        return;
                    }
                    // Summon steed
                    PacketByteBuf buf;
                    buf = PacketByteBufs.create();
                    buf.writeString("summon");

                    try {
                        ClientPlayNetworking.send(tsr_packet_id, buf);
                    } catch (IllegalStateException e) {
                        client.player.sendMessage(Text.of("Failed: Mod Not On Server?"), true);

                    }

                    // One second cooldown
                    new Thread(() -> {
                        summonCooldown = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                        summonCooldown = false;
                    }).start();

                }
        });
}

    @Override
    public void onInitializeClient() {
        createKeybinds();
    }




}
