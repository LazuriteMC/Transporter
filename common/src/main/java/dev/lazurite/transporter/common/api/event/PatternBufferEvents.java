package dev.lazurite.transporter.common.api.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.lazurite.transporter.common.api.buffer.PatternBuffer;
import dev.lazurite.transporter.common.impl.pattern.packet.PatternC2S;

/**
 * An even that is triggered when the server receives new entries for the pattern buffer.
 * @see PatternC2S
 * @since 1.0.0
 */
public final class PatternBufferEvents {
    public static final Event<PatternBufferUpdate> PATTERN_BUFFER_UPDATE = EventFactory.of((callbacks) -> (buffer) -> {
        for (var event : callbacks) {
            event.onUpdate(buffer);
        }
    });

    private PatternBufferEvents() { }

    @FunctionalInterface
    public interface PatternBufferUpdate {
        void onUpdate(PatternBuffer buffer);
    }
}