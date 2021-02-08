package dev.lazurite.transporter.impl.pattern;

import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.api.pattern.TypedPattern;
import dev.lazurite.transporter.impl.pattern.part.Quad;

import java.util.List;

public class BufferEntry<T> implements TypedPattern<T> {
    private final List<Quad> quads;
    private final T identifier;

    public BufferEntry(List<Quad> quads, T identifier) {
        this.quads = quads;
        this.identifier = identifier;
    }

    public BufferEntry(Pattern pattern, T identifier) {
        this(pattern.getQuads(), identifier);
    }

    @Override
    public T getIdentifier() {
        return identifier;
    }

    @Override
    public List<Quad> getQuads() {
        return quads;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BufferEntry) {
            return ((BufferEntry<?>) obj).getQuads().equals(getQuads()) &&
                    ((BufferEntry<?>) obj).getIdentifier().equals(getIdentifier());
        }

        return false;
    }
}
