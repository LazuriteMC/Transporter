package dev.lazurite.transporter.api.event;

import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.buffer.packet.TransportBlockBufferC2S;
import dev.lazurite.transporter.impl.buffer.packet.TransportEntityBufferC2S;
import dev.lazurite.transporter.impl.buffer.packet.TransportItemBufferC2S;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

/**
 * A set of events triggered whenever the server receives new entries for it's set of buffers.
 * <ul>
 *     <li>Block Buffer Update</li>
 *     <li>Entity Buffer Update</li>
 *     <li>Item Buffer Update</li>
 * </ul>
 * @see TransportBlockBufferC2S
 * @see TransportEntityBufferC2S
 * @see TransportItemBufferC2S
 */
public final class PatternBufferEvents {
    public static final Event<BlockBufferUpdate> BLOCK_BUFFER_UPDATE = EventFactory.createArrayBacked(BlockBufferUpdate.class, (callbacks) -> (buffer) -> {
        for (BlockBufferUpdate event : callbacks) {
            event.onUpdate(buffer);
        }
    });

    public static final Event<EntityBufferUpdate> ENTITY_BUFFER_UPDATE = EventFactory.createArrayBacked(EntityBufferUpdate.class, (callbacks) -> (buffer) -> {
        for (EntityBufferUpdate event : callbacks) {
            event.onUpdate(buffer);
        }
    });

    public static final Event<ItemBufferUpdate> ITEM_BUFFER_UPDATE = EventFactory.createArrayBacked(ItemBufferUpdate.class, (callbacks) -> (buffer) -> {
        for (ItemBufferUpdate event : callbacks) {
            event.onUpdate(buffer);
        }
    });

    private PatternBufferEvents() { }

    @FunctionalInterface
    public interface BlockBufferUpdate {
        void onUpdate(PatternBuffer<BlockPos> buffer);
    }

    @FunctionalInterface
    public interface EntityBufferUpdate {
        void onUpdate(PatternBuffer<Entity> buffer);
    }

    @FunctionalInterface
    public interface ItemBufferUpdate {
        void onUpdate(PatternBuffer<Item> buffer);
    }
}
