package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Maps;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.TypedPattern;
import dev.lazurite.transporter.impl.pattern.BufferEntry;

import java.util.Map;

/**
 * The main implementation of {@link PatternBuffer}. Can't be instantiated
 * but it contains an implementation of every {@link PatternBuffer} method.
 * @see NetworkedPatternBuffer
 * @param <T>
 */
public abstract class AbstractPatternBuffer<T> implements PatternBuffer<T> {
    protected final Map<T, TypedPattern<T>> patterns = Maps.newConcurrentMap();

    @Override
    public synchronized boolean put(T object, TypedPattern<T> pattern) {
        return patterns.put(object, (BufferEntry<T>) pattern) != null;
    }

    @Override
    public synchronized TypedPattern<T> get(T identifier) {
        return patterns.get(identifier);
    }

    @Override
    public synchronized boolean contains(T key) {
        return patterns.containsKey(key);
    }

    @Override
    public int size() {
        return patterns.size();
    }
}
