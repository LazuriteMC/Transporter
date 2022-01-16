package dev.lazurite.transporter.impl.pattern;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.lazurite.transporter.api.Disassembler;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.pattern.model.Quad;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Another implementation of {@link Pattern} other than {@link BufferEntry}, this class
 * is used by {@link Disassembler} to capture vertex information and translate it into
 * a list of {@link Quad}s.
 * @see Disassembler
 * @see Quad
 */
public class QuadConsumer implements VertexConsumer, Pattern {
    private final List<Quad> quads = Lists.newArrayList();
    private final List<Vec3> points = Lists.newArrayList();

    @Override
    public VertexConsumer vertex(double x, double y, double z) {
        points.add(new Vec3(x, y, z));
        return this;
    }

    @Override
    public VertexConsumer color(int red, int green, int blue, int alpha) {
        return this;
    }

    @Override
    public VertexConsumer uv(float f, float g) {
        return this;
    }

    @Override
    public VertexConsumer overlayCoords(int i, int j) {
        return this;
    }

    @Override
    public VertexConsumer uv2(int i, int j) {
        return this;
    }

    @Override
    public VertexConsumer normal(float f, float g, float h) {
        return this;
    }

    /**
     * For every four points, create a new {@link Quad} and add it to quads.
     */
    @Override
    public void endVertex() {
        if (points.size() >= 4) {
            quads.add(new Quad(points));
            points.clear();
        }
    }

    @Override
    public void defaultColor(int i, int j, int k, int l) {
    }

    @Override
    public void unsetDefaultColor() {
    }

    @Override
    public List<Quad> getQuads() {
        return this.quads;
    }

    /**
     * Simply compares the list of {@link Quad}s to one another.
     * @param obj the object to compare
     * @return whether or not the two objects are equivalent
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QuadConsumer) {
            return ((QuadConsumer) obj).getQuads().equals(getQuads());
        }

        return false;
    }

    public Provider asProvider() {
        return new Provider(this);
    }

    /**
     * In some instances, a {@link MultiBufferSource} is required
     * instead of a {@link VertexConsumer}. In this situation, {@link QuadConsumer#asProvider()}
     * can be called and one of these objects will be returned containing the original
     * {@link QuadConsumer}.
     */
    private static class Provider implements MultiBufferSource {
        private final QuadConsumer pattern;

        public Provider(QuadConsumer pattern) {
            this.pattern = pattern;
        }

        @Override
        public VertexConsumer getBuffer(RenderType type) {
            return pattern;
        }
    }
}