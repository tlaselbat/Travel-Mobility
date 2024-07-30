package com.tabletmc.travelsystemrevamp.net;

import com.tabletmc.travelsystemrevamp.net.packet.SummonHorsePacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.text.Text;

import static com.tabletmc.travelsystemrevamp.keybinds.RegisterKeybinds.SUMMON_HORSE;

public class RegisterClientTickEvents {
    private static boolean summonCooldown = false;
    public static void init() {
        // Summon Horse Keybind


        ClientTickEvents.END_CLIENT_TICK.register(client) -> {

            if (SUMMON_HORSE.isPressed())
                if (!summonCooldown) {
                    if (client.player != null && client.player.hasVehicle()) {
                        client.player.sendMessage(Text.of("Dismount to summon!"), true);
                        return;
                    }
                    // Summon steed
                    PacketByteBuf sbuf;
                    sbuf = PacketByteBufs.create();
                    sbuf.writeString("summon");

                    try {
                        ClientPlayNetworking.send(SummonHorsePacket.PACKET_ID, SummonHorsePacket.sbuf(
                                String.valueOf(sbuf.payload())
                                    // Replace RegistryByteBuf with PacketByteBuf in the final version of the code.
                        ));
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
        }
    }
}
