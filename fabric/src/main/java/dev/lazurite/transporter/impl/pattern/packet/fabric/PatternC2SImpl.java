package dev.lazurite.transporter.impl.pattern.packet.fabric;

import dev.lazurite.transporter.impl.Transporter;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.packet.PatternC2S;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class PatternC2SImpl {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(Transporter.MODID, "pattern_c2s");

    public static void send(BufferEntry pattern) {
        ClientPlayNetworking.send(PACKET_ID, PatternC2S.encode(pattern, new FriendlyByteBuf(Unpooled.buffer())));
    }
}