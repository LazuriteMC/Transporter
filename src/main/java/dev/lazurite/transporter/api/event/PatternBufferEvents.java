package dev.lazurite.transporter.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import dev.lazurite.transporter.api.pattern.Pattern;

/**
 * An even that is triggered when the server receives new entries for the pattern buffer.
 * @since 1.0.0
 */
public final class PatternBufferEvents {

    public static final Event<BufferUpdate> BUFFER_UPDATE = EventFactory.createArrayBacked(BufferUpdate.class, pattern -> { }, callbacks -> pattern -> {
        for (var callback : callbacks) {
            callback.onBufferUpdate(pattern);
        }
    });

    @FunctionalInterface
    public interface BufferUpdate {
        void onBufferUpdate(Pattern pattern);
    }

}