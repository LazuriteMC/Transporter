package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Lists;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.api.pattern.TypedPattern;
import dev.lazurite.transporter.impl.mixin.WorldMixin;

import java.util.List;

/**
 * This is the final implementation of {@link PatternBuffer} which is
 * used to store and sync {@link Pattern}s
 * @see WorldMixin
 * @param <T>
 */
public class NetworkedPatternBuffer<T> extends AbstractPatternBuffer<T> {
    private boolean dirty;

    /**
     * Overridden in order to modify the dirty variable.
     * @param pattern the pattern to put
     * @return whether or not it was successful or if there was a duplicate
     */
    @Override
    public boolean put(TypedPattern<T> pattern) {
        return setDirty(super.put(pattern));
    }

    /**
     * Gets every entry in the buffer.
     * @return the entire buffer
     */
    public List<TypedPattern<T>> getAll() {
        return Lists.newArrayList(patterns);
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

