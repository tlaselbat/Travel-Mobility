package com.tabletmc.travelsystemrevamp.net.packet;

import com.tabletmc.travelsystemrevamp.TravelSystemRevampConstants;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;


public record SummonHorsePacket(String tsrString) implements CustomPayload, SummonHorsePacketExt {


    public static final CustomPayload.Id<SummonHorsePacket> PACKET_ID = new CustomPayload.Id<>(TravelSystemRevampConstants.tsr_packet_id);
    public static final PacketCodec<RegistryByteBuf, SummonHorsePacket> PACKET_CODEC = PacketCodec.of(SummonHorsePacket::write, SummonHorsePacket::new);


    // Note: replaced PacketByteBuf with RegistryByteBuf
    public SummonHorsePacket(RegistryByteBuf sbuf) {
        this(sbuf.readString());
    }


    public void write(PacketByteBuf sbuf) {
        sbuf.writeString(tsrString);
    }

    public static SummonHorsePacket from(PacketByteBuf sbuf) {
        return new SummonHorsePacket(sbuf.readString());
    }



    @Override
    public Id<? extends CustomPayload> getId() {
        return null;
    }

    @Override
    public static String sbuf() {
        return null;
        //return tsrString;
    }
}