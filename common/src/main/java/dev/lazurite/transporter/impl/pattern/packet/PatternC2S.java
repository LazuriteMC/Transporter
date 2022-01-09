package dev.lazurite.transporter.impl.pattern.packet;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.lazurite.transporter.impl.Transporter;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.event.PatternBufferEvents;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.model.Quad;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * The packet responsible for transporting a given {@link Pattern} to the server.
 * @see PatternBuffer
 */
public class PatternC2S {
    /**
     * This is a platform dependent method.
     * @param pattern the {@link BufferEntry} to send to the server
     */
    @ExpectPlatform
    public static void send(BufferEntry pattern) {
        throw new AssertionError();
    }

    public static void accept(BufferEntry pattern, Executor executor) {
        executor.execute(() -> {
            final var buffer = (PatternBufferImpl) Transporter.getPatternBuffer();
            buffer.put(pattern);
            PatternBufferEvents.BUFFER_UPDATE.invoke(buffer);
        });
    }

    public static FriendlyByteBuf encode(BufferEntry pattern, FriendlyByteBuf buf) {
        buf.writeEnum(pattern.getType());
        buf.writeInt(pattern.getRegistryId());
        buf.writeInt(pattern.getQuads().size());

        for (final var quad : pattern.getQuads()) {
            quad.serialize(buf);
        }

        return buf;
    }

    public static BufferEntry decode(FriendlyByteBuf buf) {
        final var quads = new ArrayList<Quad>();
        final var type = buf.readEnum(Pattern.Type.class);
        final var registryId = buf.readInt();
        final var quadCount = buf.readInt();

        for (var j = 0; j < quadCount; j++) {
            quads.add(Quad.deserialize(buf));
        }

        return new BufferEntry(quads, type, registryId);
    }
}