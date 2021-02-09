package dev.lazurite.transporter.api.buffer;

import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.api.pattern.TypedPattern;
import dev.lazurite.transporter.impl.buffer.AbstractPatternBuffer;
import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A pattern buffer is a list of {@link Pattern} objects that
 * represent either a block, entity, or item.
 * @see AbstractPatternBuffer
 * @see NetworkedPatternBuffer
 * @param <T>
 */
public interface PatternBuffer<T> {
    /**
     * Retrieve the block buffer from the given {@link World}.
     * @param world the {@link World} to get the buffer from
     * @return the block buffer
     */
    static PatternBuffer<BlockPos> getBlockBuffer(World world) {
        return ((BufferStorage) world).getBlockBuffer();
    }

    /**
     * Retrieve the entity buffer from the given {@link World}.
     * @param world the {@link World} to get the buffer from
     * @return the entity buffer
     */
    static PatternBuffer<Entity> getEntityBuffer(World world) {
        return ((BufferStorage) world).getEntityBuffer();
    }

    /**
     * Retrieve the item buffer from the given {@link World}.
     * @param world the {@link World} to get the buffer from
     * @return the item buffer
     */
    static PatternBuffer<Item> getItemBuffer(World world) {
        return ((BufferStorage) world).getItemBuffer();
    }


    /**
     * This is the only way to add to a buffer. You should only call
     * this method from the logical client side of the game. You should
     * never have to call this from the server.<br>
     * Once a {@link TypedPattern} is added to the buffer on the client, the
     * next time a synchronization occurs will be when that {@link Pattern} is
     * sent to the server's master buffer.
     * @param pattern the {@link TypedPattern} to add
     * @return whether or not it was a duplicate entry
     */
    boolean put(TypedPattern<T> pattern);

    /**
     * This method returns a {@link TypedPattern} that matches the provided
     * identifier. The identifier can be either a block, entity, or item. Since
     * no duplicate entries are allowed within the buffer, this will always yield
     * one result.
     * @param identifier the identifier of type T
     * @return the matching {@link TypedPattern}
     */
    TypedPattern<T> get(T identifier);

    /**
     * Similar to the above get method, this method simply returns true if the
     * buffer contains an entry matching the identifier or false if it does not.
     * @param identifier the identifier of type T
     * @return whether or not there exists a matching {@link TypedPattern} entry
     */
    boolean contains(T identifier);

    /**
     * Gets the size of the buffer.
     * @return the size
     */
    int size();
}
