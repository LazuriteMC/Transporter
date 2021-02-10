package dev.lazurite.transporter.impl.pattern;

import dev.lazurite.transporter.api.Disassembler;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.api.pattern.TypedPattern;
import dev.lazurite.transporter.impl.buffer.AbstractPatternBuffer;
import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import dev.lazurite.transporter.impl.pattern.part.Quad;

import java.util.List;

/**
 * The main implementation of {@link Pattern} and {@link TypedPattern}. This is
 * what is stored within an {@link AbstractPatternBuffer}.
 * @see NetworkedPatternBuffer
 * @see AbstractPatternBuffer
 * @see Disassembler
 * @param <T>
 */
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

    /**
     * Compares both the list of {@link Quad}s and the identifier of type T.
     * @param obj the object to compare
     * @return whether or not they're equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BufferEntry) {
            return ((BufferEntry<?>) obj).getQuads().equals(getQuads()) &&
                    ((BufferEntry<?>) obj).getIdentifier().equals(getIdentifier());
        }

        return false;
    }
}
