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
import net.minecraft.core.Direction;
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
        final var quads = new ArrayList<Quad>();
        final var identifier = buf.readResourceLocation();
        final var quadCount = buf.readInt();
        final var intDirection = buf.readInt();

        for (var j = 0; j < quadCount; j++) {
            quads.add(Quad.deserialize(buf));
        }

        server.execute(() -> {
            final var buffer = (PatternBufferImpl) Transporter.getPatternBuffer();
            buffer.put(new BufferEntry(quads, identifier, intDirection == -1 ? null : Direction.from2DDataValue(intDirection)));
            PatternBufferEvents.PATTERN_BUFFER_UPDATE.invoker().onUpdate(buffer);
        });
    }

    public static void send(BufferEntry pattern) {
        var buf = PacketByteBufs.create();
        buf.writeResourceLocation(pattern.getResourceLocation());
        buf.writeInt(pattern.getQuads().size());
        buf.writeInt(pattern.getDirection().isPresent() ?
                pattern.getDirection().get().get2DDataValue() : -1);

        for (var quad : pattern.getQuads()) {
            quad.serialize(buf);
        }

        ClientPlayNetworking.send(PACKET_ID, buf);
    }
}