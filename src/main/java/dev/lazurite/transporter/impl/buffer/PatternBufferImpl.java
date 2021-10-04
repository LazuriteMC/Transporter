package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Maps;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

/**
 * The main implementation of {@link PatternBuffer}.
 * It contains an implementation {@link PatternBuffer}.
 */
public class PatternBufferImpl implements PatternBuffer {
    protected final Map<ResourceLocation, BufferEntry> patterns = Maps.newConcurrentMap();

    @Override
    public Pattern get(ResourceLocation resourceLocation) {
        return patterns.get(resourceLocation);
    }

    @Override
    public boolean contains(ResourceLocation resourceLocation) {
        return patterns.containsKey(resourceLocation);
    }

    @Override
    public int size() {
        return patterns.size();
    }

    public void put(Pattern pattern) {
        patterns.put(((BufferEntry) pattern).getResourceLocation(), (BufferEntry) pattern);
    }

    public void clear() {
        patterns.clear();
    }

    public Map<ResourceLocation, BufferEntry> getAll() {
        return Maps.newHashMap(patterns);
    }
}
