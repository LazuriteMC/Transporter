package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Maps;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The main implementation of {@link PatternBuffer}.
 * It contains an implementation {@link PatternBuffer}.
 */
public class PatternBufferImpl implements PatternBuffer {
    protected final Map<ResourceLocation, List<BufferEntry>> patterns = Maps.newConcurrentMap();

    @Override
    public Pattern get(ResourceLocation resourceLocation) {
        final var entries = patterns.get(resourceLocation);

        if (entries != null && entries.size() > 0) {
            return entries.get(0);
        }

        return null;
    }

    @Override
    public Pattern get(ResourceLocation resourceLocation, Direction direction) {
        final var entries = patterns.get(resourceLocation);

        for (var entry : entries) {
            final var directionOptional = entry.getDirection();

            if (directionOptional.isPresent() && directionOptional.get() == direction) {
                return entry;
            }
        }

        return null;
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
        final var bufferEntry = (BufferEntry) pattern;
        final var resourceLocation = bufferEntry.getResourceLocation();
        patterns.computeIfAbsent(resourceLocation, k -> new ArrayList<>());
        patterns.get(resourceLocation).add(bufferEntry);
    }

    public void clear() {
        patterns.clear();
    }

    public Map<ResourceLocation, List<BufferEntry>> getAll() {
        return Maps.newHashMap(patterns);
    }
}
