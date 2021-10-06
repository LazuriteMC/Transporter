package dev.lazurite.transporter.api.event;

import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.pattern.packet.PatternC2S;
import net.minecraftforge.eventbus.api.Event;

/**
 * An even that is triggered when the server receives new entries for the pattern buffer.
 * @see PatternC2S
 * @since 1.0.0
 */
public final class PatternBufferEvents extends Event {
    private final PatternBuffer buffer;

    public PatternBufferEvents(PatternBuffer buffer) {
        this.buffer = buffer;
    }

    public PatternBuffer getBuffer() {
        return buffer;
    }
}
