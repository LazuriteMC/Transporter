package dev.lazurite.transporter.impl.pattern;

import dev.lazurite.transporter.api.Disassembler;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.impl.pattern.model.Quad;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * The main implementation of {@link Pattern}. This is
 * what is stored within a {@link PatternBufferImpl}.
 * @see PatternBufferImpl
 * @see Disassembler
 */
public class BufferEntry implements Pattern {
    private final List<Quad> quads;
    private final Identifier identifier;

    public  BufferEntry(List<Quad> quads, Identifier identifier) {
        this.quads = quads;
        this.identifier = identifier;
    }

    public BufferEntry(Pattern pattern, Identifier identifier) {
        this(pattern.getQuads(), identifier);
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public List<Quad> getQuads() {
        return this.quads;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BufferEntry) {
            return ((BufferEntry) obj).getQuads().equals(getQuads()) &&
                    ((BufferEntry) obj).getIdentifier().equals(getIdentifier());
        }

        return false;
    }
}
