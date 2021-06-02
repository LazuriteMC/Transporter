package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Maps;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import net.minecraft.util.Identifier;

import java.util.Map;

/**
 * The main implementation of {@link PatternBuffer}.
 * It contains an implementation {@link PatternBuffer}.
 */
public class PatternBufferImpl implements PatternBuffer {
    protected final Map<Identifier, BufferEntry> patterns = Maps.newConcurrentMap();

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

    public void put(Pattern pattern) {
        patterns.put(((BufferEntry) pattern).getIdentifier(), (BufferEntry) pattern);
    }

    public void clear() {
        patterns.clear();
    }

    public Map<Identifier, BufferEntry> getAll() {
        return Maps.newHashMap(patterns);
    }
}
