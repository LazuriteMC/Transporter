package dev.lazurite.transporter.impl.buffer.storage;

import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import dev.lazurite.transporter.impl.mixin.WorldMixin;
import net.minecraft.world.World;

/**
 * This interface that can be assigned to a {@link World}
 * to be able to obtain the {@link PatternBuffer} from it.
 * @see WorldMixin
 * @see PatternBuffer
 */
public interface BufferStorage {
    NetworkedPatternBuffer getPatternBuffer();
}
