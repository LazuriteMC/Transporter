package dev.lazurite.transporter.api;

import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.api.pattern.TypedPattern;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.QuadConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * The calls in this class are important in that they're necessary
 * in order for anything within this library to work. From here, you can
 * obtain a {@link Pattern} based on a block, entity, or item.
 * <br><br><b>IMPORTANT: This can only be executed on the physical client.</b><br><br>
 * Once a {@link Pattern} has been obtained from here, you must place it in the correct
 * {@link PatternBuffer} in order for it to be synced to the server using {@link PatternBuffer#put(TypedPattern)}.
 * <br><br>
 * In each of these methods, you have the option to transform the pattern any way you want before it
 * "sets" into a {@link Pattern}. You can do this by passing in a pre-manipulated {@link MatrixStack}.
 */
@Environment(EnvType.CLIENT)
public interface Disassembler {
    static TypedPattern<BlockPos> getBlock(BlockState blockState, BlockPos blockPos, BlockRenderView blockView, @Nullable MatrixStack transformation) {
        if (transformation == null) {
            transformation = new MatrixStack();
        }

        QuadConsumer consumer = new QuadConsumer();
        MinecraftClient.getInstance().getBlockRenderManager()
                .renderBlock(blockState, blockPos, blockView, transformation, consumer, false, new Random());

        return new BufferEntry<>(consumer, blockPos);
    }

    static TypedPattern<Entity> getEntity(Entity entity, @Nullable MatrixStack transformation) {
        if (transformation == null) {
            transformation = new MatrixStack();
        }

        QuadConsumer consumer = new QuadConsumer();
        MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity)
                .render(entity, 0, 0, transformation, consumer.asProvider(), 0);

        return new BufferEntry<>(consumer, entity);
    }

    static TypedPattern<Item> getItem(Item item, @Nullable MatrixStack transformation) {
        if (transformation == null) {
            transformation = new MatrixStack();
        }

        QuadConsumer consumer = new QuadConsumer();
        MinecraftClient.getInstance().getItemRenderer()
                .renderItem(new ItemStack(item), ModelTransformation.Mode.GROUND, 0, 0, transformation, consumer.asProvider());

        return new BufferEntry<>(consumer, item);
    }
}
