package com.tabletmc.travelsystemrevamp.net;

import com.tabletmc.travelsystemrevamp.net.impl.TSRServerPlayerExt;
import com.tabletmc.travelsystemrevamp.net.packet.SummonHorsePacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import static com.tabletmc.travelsystemrevamp.TravelSystemRevampConstants.tsr_packet_id;

public class ServerNetworking {
    public static void init() {
        PayloadTypeRegistry.playC2S().register(SummonHorsePacket.PACKET_ID, SummonHorsePacket.PACKET_CODEC);




        ServerPlayNetworking.registerGlobalReceiver(SummonHorsePacket.PACKET_ID, (payload, context) -> {
            var sbuf = payload.sbuf();
            if (sbuf.equals("summon")) {
                var player = context.player();
                ((TSRServerPlayerExt)player).summonHorse(true);
            }
        });
    }
}
