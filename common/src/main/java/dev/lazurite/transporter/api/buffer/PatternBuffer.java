package dev.lazurite.transporter.api.buffer;

import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/**
 * A pattern buffer is a list of {@link Pattern} objects that
 * represent either a block, entity, or item.
 * @see PatternBufferImpl
 * @since 1.0.0
 */
public interface PatternBuffer {
    /**
     * This method returns a {@link Pattern} that matches the provided
     * identifier. Since no duplicate entries are allowed within the
     * buffer, this will always yield one result.
     * @param resourceLocation the resource location of the pattern
     * @return the matching {@link Pattern}
     */
    Pattern get(ResourceLocation resourceLocation);

    /**
     * This method does the same as the above {@link PatternBuffer#get(ResourceLocation)}
     * but it allows you to specify a direction as well. This only works for blocks
     * and block entities.
     * @param resourceLocation the resource location of the pattern
     * @param direction the matching {@link Pattern}
     * @return the mathcing {@link Pattern}
     */
    Pattern get(ResourceLocation resourceLocation, Direction direction);

    /**
     * Similar to the above get method, this method simply returns true if the
     * buffer contains an entry matching the identifier or false if it does not.
     * @param resourceLocation the resource location of the pattern
     * @return whether there exists a matching {@link Pattern} entry
     */
    boolean contains(ResourceLocation resourceLocation);

    /**
     * Gets the size of the buffer.
     * @return the size
     */
    int size();
}