package dev.lazurite.transporter.common.impl.pattern.packet;

import dev.architectury.networking.NetworkManager;
import dev.lazurite.transporter.common.impl.Transporter;
import dev.lazurite.transporter.common.api.pattern.Pattern;
import dev.lazurite.transporter.common.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.common.api.buffer.PatternBuffer;
import dev.lazurite.transporter.common.api.event.PatternBufferEvents;
import dev.lazurite.transporter.common.impl.pattern.BufferEntry;
import dev.lazurite.transporter.common.impl.pattern.model.Quad;
import io.netty.buffer.Unpooled;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

/**
 * The packet responsible for transporting a given {@link Pattern} to the server.
 * @see PatternBuffer
 */
public class PatternC2S {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(Transporter.MODID, "pattern_c2s");

    public static void accept(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        final var quads = new ArrayList<Quad>();
        final var identifier = buf.readResourceLocation();
        final var quadCount = buf.readInt();
        final var intDirection = buf.readInt();

        for (var j = 0; j < quadCount; j++) {
            quads.add(Quad.deserialize(buf));
        }

        context.queue(() -> {
            final var buffer = (PatternBufferImpl) Transporter.getPatternBuffer();
            buffer.put(new BufferEntry(quads, identifier, intDirection == -1 ? null : Direction.from2DDataValue(intDirection)));
            PatternBufferEvents.PATTERN_BUFFER_UPDATE.invoker().onUpdate(buffer);
        });
    }

    public static void send(BufferEntry pattern) {
        final var buf = new FriendlyByteBuf(Unpooled.buffer());

        buf.writeResourceLocation(pattern.getResourceLocation());
        buf.writeInt(pattern.getQuads().size());
        buf.writeInt(pattern.getDirection().isPresent() ?
                pattern.getDirection().get().get2DDataValue() : -1);

        for (var quad : pattern.getQuads()) {
            quad.serialize(buf);
        }

        NetworkManager.sendToServer(PACKET_ID, buf);
    }
}