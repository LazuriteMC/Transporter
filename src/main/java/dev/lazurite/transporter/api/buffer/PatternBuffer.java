package dev.lazurite.transporter.api.buffer;

import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.buffer.AbstractPatternBuffer;
import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import dev.lazurite.transporter.impl.buffer.storage.BufferStorage;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * A pattern buffer is a list of {@link Pattern} objects that
 * represent either a block, entity, or item.
 * @see AbstractPatternBuffer
 * @see NetworkedPatternBuffer
 * @since 1.0.0
 */
public interface PatternBuffer {
    /**
     * Retrieve the pattern buffer from the given {@link World}.
     * @param world the {@link World} to get the buffer from
     * @return the block buffer
     */
    static PatternBuffer getPatternBuffer(World world) {
        return ((BufferStorage) world).getPatternBuffer();
    }

    /**
     * This is the only way to add to a buffer. You should only call
     * this method from the logical client side of the game. You should
     * never have to call this from the server.<br>
     * Once a {@link Pattern} is added to the buffer on the client, the
     * next time a synchronization occurs will be when that {@link Pattern} is
     * sent to the server's master buffer.
     * @param pattern the {@link Pattern} to add
     * @return whether or not it was a duplicate entry
     */
    boolean put(Pattern pattern);

    /**
     * This method returns a {@link Pattern} that matches the provided
     * identifier. Since no duplicate entries are allowed within the
     * buffer, this will always yield one result.
     * @param identifier the identifier of the pattern
     * @return the matching {@link Pattern}
     */
    Pattern get(Identifier identifier);

    /**
     * Similar to the above get method, this method simply returns true if the
     * buffer contains an entry matching the identifier or false if it does not.
     * @param identifier the identifier of the pattern
     * @return whether or not there exists a matching {@link Pattern} entry
     */
    boolean contains(Identifier identifier);

    /**
     * Gets the size of the buffer.
     * @return the size
     */
    int size();
}
