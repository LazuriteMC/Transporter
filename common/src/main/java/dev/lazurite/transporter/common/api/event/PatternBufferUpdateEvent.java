package dev.lazurite.transporter.common.api.event;

import dev.lazurite.transporter.common.api.buffer.PatternBuffer;
import dev.lazurite.transporter.common.impl.pattern.packet.PatternC2S;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * An even that is triggered when the server receives new entries for the pattern buffer.
 * @see PatternC2S
 * @since 1.0.0
 */
public final class PatternBufferUpdateEvent {
    private static final ArrayList<Consumer<PatternBuffer>> events = new ArrayList<>();

    public static void register(Consumer<PatternBuffer> consumer) {
        events.add(consumer);
    }

    public static void invokeAll(PatternBuffer buffer) {
        for (final var event : events) {
            event.accept(buffer);
        }
    }
}