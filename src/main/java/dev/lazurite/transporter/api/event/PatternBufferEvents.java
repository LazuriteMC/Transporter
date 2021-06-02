package dev.lazurite.transporter.api.event;

import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.pattern.packet.PatternC2S;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * An even that is triggered when the server receives new entries for the pattern buffer.
 * @see PatternC2S
 * @since 1.0.0
 */
public final class PatternBufferEvents {
    public static final Event<PatternBufferUpdate> PATTERN_BUFFER_UPDATE = EventFactory.createArrayBacked(PatternBufferUpdate.class, (callbacks) -> (buffer) -> {
        for (PatternBufferUpdate event : callbacks) {
            event.onUpdate(buffer);
        }
    });

    private PatternBufferEvents() {
    }

    @FunctionalInterface
    public interface PatternBufferUpdate {
        void onUpdate(PatternBuffer buffer);
    }
}
