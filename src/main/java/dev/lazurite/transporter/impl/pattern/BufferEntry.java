package dev.lazurite.transporter.impl.pattern;

import dev.lazurite.transporter.api.Disassembler;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.impl.pattern.model.Quad;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * The main implementation of {@link Pattern}. This is
 * what is stored within a {@link PatternBufferImpl}.
 * @see PatternBufferImpl
 * @see Disassembler
 */
public class BufferEntry implements Pattern {
    private final List<Quad> quads;
    private final ResourceLocation resourceLocation;

    public  BufferEntry(List<Quad> quads, ResourceLocation resourceLocation) {
        this.quads = quads;
        this.resourceLocation = resourceLocation;
    }

    public BufferEntry(Pattern pattern, ResourceLocation resourceLocation) {
        this(pattern.getQuads(), resourceLocation);
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    @Override
    public List<Quad> getQuads() {
        return this.quads;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BufferEntry) {
            return ((BufferEntry) obj).getQuads().equals(getQuads()) &&
                    ((BufferEntry) obj).getResourceLocation().equals(getResourceLocation());
        }

        return false;
    }
}
