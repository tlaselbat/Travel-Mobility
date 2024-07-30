package com.tabletmc.travelsystemrevamp.net;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SummonKey(String summon) {
    public static final PacketCodec<PacketByteBuf, SummonKey> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, SummonKey::summon,
            SummonKey::new);
    public String getName() {
        return summon;
    }
    public SummonKey(String summon) {
        this.summon = summon;
    }
    public void encode(PacketByteBuf buf) {
        buf.writeString(summon);
    }
    public static SummonKey decode(PacketByteBuf buf) {
        return new SummonKey(buf.readString());
    }
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return null;
    }
    public String sbuf() {
        return "SummonKey{" +
                "summon='" + summon + '\'' +
                '}';
    }
    public String toString() {
        return sbuf();
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SummonKey)) return false;
        SummonKey that = (SummonKey) o;
        return summon.equals(that.summon);
        
    }
    public int hashCode() {
        return summon.hashCode();
    }

    public static void main(String[] args) {
        PacketByteBuf buf = PacketByteBuf.create();
        SummonKey key = new SummonKey("summon");
        key.encode(buf);
        buf.resetReaderIndex();
        SummonKey decodedKey = SummonKey.decode(buf);
        System.out.println("Decoded key: " + decodedKey);
    }

}