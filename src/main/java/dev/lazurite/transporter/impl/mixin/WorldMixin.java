package dev.lazurite.transporter.impl.mixin;

import dev.lazurite.transporter.impl.buffer.storage.BufferStorage;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * This mixin uses any {@link World} object as storage for {@link PatternBuffer}s.
 * @see BufferStorage
 */
@Mixin(World.class)
public class WorldMixin implements BufferStorage {
    @Unique private final NetworkedPatternBuffer patternBuffer = new NetworkedPatternBuffer();

    @Unique @Override
    public NetworkedPatternBuffer getPatternBuffer() {
        return patternBuffer;
    }
}
