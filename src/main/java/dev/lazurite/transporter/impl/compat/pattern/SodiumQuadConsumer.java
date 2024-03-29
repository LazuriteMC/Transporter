package dev.lazurite.transporter.impl.compat.pattern;

import net.caffeinemc.mods.sodium.api.vertex.buffer.VertexBufferWriter;
import net.caffeinemc.mods.sodium.api.vertex.format.VertexFormatDescription;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import dev.lazurite.transporter.impl.pattern.QuadConsumer;
import dev.lazurite.transporter.impl.pattern.model.Quad;

public class SodiumQuadConsumer extends QuadConsumer implements VertexBufferWriter {

    SodiumQuadConsumer() {
    }

    @Override
    public void push(MemoryStack stack, long ptr, int count, VertexFormatDescription format) {
        if (count == 4) {
            for (int i = 0; i < 4; i++) {
                var point = read(ptr);
                this.points.add(point);
            }
            quads.add(new Quad(points));
            points.clear();
        }

    }

    private static Vec3 read(long ptr) {
        float x = MemoryUtil.memGetFloat(ptr);
        float y = MemoryUtil.memGetFloat(ptr + 4);
        float z = MemoryUtil.memGetFloat(ptr + 8);
        return new Vec3(x, y, z);
    }

}
