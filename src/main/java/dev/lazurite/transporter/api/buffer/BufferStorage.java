package dev.lazurite.transporter.api.buffer;

import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import dev.lazurite.transporter.impl.mixin.WorldMixin;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This interface that can be assigned to a {@link World}
 * object to obtain three different {@link PatternBuffer}s
 * (block, entity, and item).
 * @see WorldMixin
 * @see PatternBuffer
 */
public interface BufferStorage {
    NetworkedPatternBuffer<BlockPos> getBlockBuffer();

    NetworkedPatternBuffer<Entity> getEntityBuffer();

    NetworkedPatternBuffer<Item> getItemBuffer();
}
