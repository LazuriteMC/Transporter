package dev.lazurite.transporter.api.pattern;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

/**
 * An extension of the basic {@link Pattern}, this
 * pattern also contains an identifier which can be
 * either a {@link BlockPos}, {@link Entity}, or {@link Item}.
 * @param <T>
 * @see Pattern
 */
public interface TypedPattern<T> extends Pattern {
    T getIdentifier();
}
