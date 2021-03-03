package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Lists;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.TypedPattern;
import dev.lazurite.transporter.impl.pattern.BufferEntry;

import java.util.Collections;
import java.util.List;

/**
 * The main implementation of {@link PatternBuffer}. Can't be instantiated
 * but it contains an implementation of every {@link PatternBuffer} method.
 * @see NetworkedPatternBuffer
 * @param <T>
 */
public abstract class AbstractPatternBuffer<T> implements PatternBuffer<T> {
    protected final List<BufferEntry<T>> patterns = Collections.synchronizedList(Lists.newArrayList());

    @Override
    public boolean put(TypedPattern<T> pattern) {
        if (!patterns.contains((BufferEntry<T>) pattern)) {
            patterns.removeIf(entry -> entry.getIdentifier().equals(pattern.getIdentifier()));
            patterns.add((BufferEntry<T>) pattern);
            return true;
        }

        return false;
    }

    @Override
    public TypedPattern<T> get(T identifier) {
        for (BufferEntry<T> pattern : patterns) {
            if (pattern.getIdentifier().equals(identifier)) {
                return pattern;
            }
        }

        return null;
    }

    @Override
    public boolean contains(T key) {
        for (TypedPattern<T> pattern : patterns) {
            if (pattern.getIdentifier().equals(key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int size() {
        return patterns.size();
    }
}
