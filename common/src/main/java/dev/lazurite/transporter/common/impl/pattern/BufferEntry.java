package dev.lazurite.transporter.common.impl.pattern;

import dev.lazurite.transporter.common.api.Disassembler;
import dev.lazurite.transporter.common.api.pattern.Pattern;
import dev.lazurite.transporter.common.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.common.impl.pattern.model.Quad;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

/**
 * The main implementation of {@link Pattern}. This is
 * what is stored within a {@link PatternBufferImpl}.
 * @see PatternBufferImpl
 * @see Disassembler
 */
public class BufferEntry implements Pattern {
    private final List<Quad> quads;
    private final ResourceLocation resourceLocation;
    private final Direction direction;

    public  BufferEntry(List<Quad> quads, ResourceLocation resourceLocation, Direction direction) {
        this.quads = quads;
        this.resourceLocation = resourceLocation;
        this.direction = direction;
    }

    public BufferEntry(Pattern pattern, ResourceLocation resourceLocation, Direction direction) {
        this(pattern.getQuads(), resourceLocation, direction);
    }

    public BufferEntry(Pattern pattern, ResourceLocation resourceLocation) {
        this(pattern, resourceLocation, null);
    }

    public BufferEntry(List<Quad> quads, ResourceLocation resourceLocation) {
        this(quads, resourceLocation, null);
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    @Override
    public List<Quad> getQuads() {
        return this.quads;
    }

    public Optional<Direction> getDirection() {
        return Optional.ofNullable(this.direction);
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