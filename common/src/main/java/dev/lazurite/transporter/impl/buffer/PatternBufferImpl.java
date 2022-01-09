package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Maps;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.pattern.BufferEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The main implementation of {@link PatternBuffer}.
 * It contains an implementation {@link PatternBuffer}.
 */
public class PatternBufferImpl implements PatternBuffer {
    protected final Map<Integer, BufferEntry> entityPatterns = Maps.newConcurrentMap();
    protected final Map<Integer, BufferEntry> itemPatterns = Maps.newConcurrentMap();
    protected final Map<Integer, BufferEntry> blockPatterns = Maps.newConcurrentMap();

    @Override
    public Pattern get(Pattern.Type type, int registryId) {
        return switch (type) {
            case ENTITY -> entityPatterns.get(registryId);
            case ITEM -> itemPatterns.get(registryId);
            case BLOCK -> blockPatterns.get(registryId);
        };
    }

    @Override
    public boolean contains(Pattern.Type type, int registryId) {
        return switch (type) {
            case ENTITY -> entityPatterns.containsKey(registryId);
            case ITEM -> itemPatterns.containsKey(registryId);
            case BLOCK -> blockPatterns.containsKey(registryId);
        };
    }

    @Override
    public int size() {
        return entityPatterns.size() + itemPatterns.size() + blockPatterns.size();
    }

    public void put(Pattern pattern) {
        final var bufferEntry = (BufferEntry) pattern;
        final var type = bufferEntry.getType();
        final var registryId = bufferEntry.getRegistryId();

        switch (type) {
            case ENTITY -> entityPatterns.put(registryId, (BufferEntry) pattern);
            case ITEM -> itemPatterns.put(registryId, (BufferEntry) pattern);
            case BLOCK -> blockPatterns.put(registryId, (BufferEntry) pattern);
        }
    }

    public void clear() {
        entityPatterns.clear();
        itemPatterns.clear();
        blockPatterns.clear();
    }

    public List<BufferEntry> getAll() {
        final var out = new ArrayList<BufferEntry>();
        out.addAll(entityPatterns.values());
        out.addAll(itemPatterns.values());
        out.addAll(blockPatterns.values());
        return out;
    }
}