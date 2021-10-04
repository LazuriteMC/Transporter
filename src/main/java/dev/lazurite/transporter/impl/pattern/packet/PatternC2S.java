package dev.lazurite.transporter.impl.pattern.packet;

import dev.lazurite.transporter.Transporter;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.event.PatternBufferEvents;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.model.Quad;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;

/**
 * The packet responsible for transporting a given {@link Pattern} to the server.
 * @see PatternBuffer
 */
public class PatternC2S {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(Transporter.MODID, "pattern_c2s");

    public static void accept(MinecraftServer server, ServerPlayer player, ServerGamePacketListener handler, FriendlyByteBuf buf, PacketSender sender) {
        var quads = new ArrayList<Quad>();
        var identifier = buf.readResourceLocation();
        var quadCount = buf.readInt();

        for (var j = 0; j < quadCount; j++) {
            quads.add(Quad.deserialize(buf));
        }

        server.execute(() -> {
            var buffer = (PatternBufferImpl) Transporter.getPatternBuffer();
            buffer.put(new BufferEntry(quads, identifier));
            PatternBufferEvents.PATTERN_BUFFER_UPDATE.invoker().onUpdate(buffer);
        });
    }

    public static void send(BufferEntry pattern) {
        var buf = PacketByteBufs.create();
        buf.writeResourceLocation(pattern.getResourceLocation());
        buf.writeInt(pattern.getQuads().size());

        for (var quad : pattern.getQuads()) {
            quad.serialize(buf);
        }

        ClientPlayNetworking.send(PACKET_ID, buf);
    }
}