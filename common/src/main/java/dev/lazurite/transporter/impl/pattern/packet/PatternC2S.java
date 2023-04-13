package dev.lazurite.transporter.impl.pattern.packet;

import dev.lazurite.toolbox.api.network.ClientNetworking;
import dev.lazurite.transporter.impl.Transporter;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.event.PatternBufferEvents;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.model.Quad;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

/**
 * The packet responsible for transporting a given {@link Pattern} to the server.
 * @see PatternBuffer
 */
public class PatternC2S {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation("transporter", "pattern");

    public static void send(BufferEntry pattern) {
        ClientNetworking.send(IDENTIFIER, buf -> {
            buf.writeEnum(pattern.getType());
            buf.writeInt(pattern.getRegistryId());
            buf.writeInt(pattern.getQuads().size());

            for (final var quad : pattern.getQuads()) {
                quad.serialize(buf);
            }
        });
    }

    public static void accept(FriendlyByteBuf buf) {
        final var quads = new ArrayList<Quad>();
        final var type = buf.readEnum(Pattern.Type.class);
        final var registryId = buf.readInt();
        final var quadCount = buf.readInt();

        for (var j = 0; j < quadCount; j++) {
            quads.add(Quad.deserialize(buf));
        }

        final var entry = new BufferEntry(quads, type, registryId);
        final var buffer = (PatternBufferImpl) Transporter.getPatternBuffer();
        buffer.put(entry);

        PatternBufferEvents.BUFFER_UPDATE.invoke(buffer);
    }
}