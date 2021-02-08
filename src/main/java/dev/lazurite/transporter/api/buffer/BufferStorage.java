package dev.lazurite.transporter.api.buffer;

import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public interface BufferStorage {
    NetworkedPatternBuffer<BlockPos> getBlockBuffer();
    NetworkedPatternBuffer<Entity> getEntityBuffer();
    NetworkedPatternBuffer<Item> getItemBuffer();
}
