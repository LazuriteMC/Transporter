package dev.lazurite.transporter.common.impl.pattern.packet;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.lazurite.transporter.common.impl.Transporter;
import dev.lazurite.transporter.common.api.pattern.Pattern;
import dev.lazurite.transporter.common.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.common.api.buffer.PatternBuffer;
import dev.lazurite.transporter.common.api.event.PatternBufferEvents;
import dev.lazurite.transporter.common.impl.pattern.BufferEntry;
import dev.lazurite.transporter.common.impl.pattern.model.Quad;
import net.minecraft.core.Direction;
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
        buf.writeResourceLocation(pattern.getResourceLocation());
        buf.writeInt(pattern.getQuads().size());
        buf.writeInt(pattern.getDirection().isPresent() ?
                pattern.getDirection().get().get2DDataValue() : -1);

        for (final var quad : pattern.getQuads()) {
            quad.serialize(buf);
        }

        return buf;
    }

    public static BufferEntry decode(FriendlyByteBuf buf) {
        final var quads = new ArrayList<Quad>();
        final var identifier = buf.readResourceLocation();
        final var quadCount = buf.readInt();
        final var intDirection = buf.readInt();

        for (var j = 0; j < quadCount; j++) {
            quads.add(Quad.deserialize(buf));
        }

        return new BufferEntry(quads, identifier, intDirection == -1 ? null : Direction.from2DDataValue(intDirection));
    }
}