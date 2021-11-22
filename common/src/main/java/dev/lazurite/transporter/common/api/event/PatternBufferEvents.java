package dev.lazurite.transporter.common.api.event;

import dev.lazurite.toolbox.api.event.Event;
import dev.lazurite.transporter.common.api.buffer.PatternBuffer;
import dev.lazurite.transporter.common.impl.pattern.packet.PatternC2S;

/**
 * An even that is triggered when the server receives new entries for the pattern buffer.
 * @see PatternC2S
 * @since 1.0.0
 */
public final class PatternBufferEvents {
    public static Event<BufferUpdate> BUFFER_UPDATE = Event.create();

    private PatternBufferEvents() { }

    @FunctionalInterface
    public interface BufferUpdate {
        void onBufferUpdate(PatternBuffer patternBuffer);
    }
}