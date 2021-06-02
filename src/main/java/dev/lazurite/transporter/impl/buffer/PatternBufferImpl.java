package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Maps;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import net.minecraft.util.Identifier;

import java.util.Map;

/**
 * The main implementation of {@link PatternBuffer}. Can't be instantiated
 * but it contains an implementation of every {@link PatternBuffer} method.
 * @see NetworkedPatternBuffer
 */
public abstract class AbstractPatternBuffer implements PatternBuffer {
    protected final Map<Identifier, BufferEntry> patterns = Maps.newConcurrentMap();

    @Override
    public boolean put(Pattern pattern) {
        return patterns.put(((BufferEntry) pattern).getIdentifier(), (BufferEntry) pattern) != null;
    }

    @Override
    public Pattern get(Identifier identifier) {
        return patterns.get(identifier);
    }

    @Override
    public boolean contains(Identifier identifier) {
        return patterns.containsKey(identifier);
    }

    @Override
    public int size() {
        return patterns.size();
    }
}
