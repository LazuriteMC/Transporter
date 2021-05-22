package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Maps;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.mixin.WorldMixin;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import net.minecraft.util.Identifier;

import java.util.Map;

/**
 * This is the final implementation of {@link PatternBuffer} which is
 * used to store and sync {@link Pattern}s
 * @see WorldMixin
 */
public class NetworkedPatternBuffer extends AbstractPatternBuffer {
    private boolean dirty;

    /**
     * Overridden in order to modify the dirty variable.
     * @param pattern the pattern to put
     * @return whether or not it was successful or if there was a duplicate
     */
    @Override
    public boolean put(Pattern pattern) {
        super.put(pattern);
        return setDirty(true);
    }

    /**
     * Gets every entry in the buffer.
     * @return the entire buffer
     */
    public Map<Identifier, BufferEntry> getAll() {
        return Maps.newHashMap(patterns);
    }

    /**
     * Removes every entry within the buffer.
     */
    public void clear() {
        patterns.clear();
    }

    public boolean setDirty(boolean dirty) {
        this.dirty = dirty;
        return this.dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
}