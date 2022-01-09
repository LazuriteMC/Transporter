package dev.lazurite.transporter.api;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.pattern.packet.PatternC2S;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.QuadConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * The calls in this class are important in that they're necessary in order for anything within this library to work.
 * From here, you can obtain a {@link Pattern} based on a block, block entity, entity, or item.
 * <br><br><b>IMPORTANT: This can only be executed on the physical client.</b><br><br>
 * In each of these methods, you have the option to transform the pattern any way you want before it
 * "sets" into a {@link Pattern}. You can do this by passing in a pre-manipulated {@link PoseStack}.
 * @see PatternBuffer
 * @see Pattern
 * @since 1.0.0
 */
@Environment(EnvType.CLIENT)
public interface Disassembler {
    static Pattern getBlock(BlockState blockState, @Nullable PoseStack transformation) {
        if (transformation == null) {
            transformation = new PoseStack();
        }

        final var consumer = new QuadConsumer();
        final var model = Minecraft.getInstance().getBlockRenderer().getBlockModel(blockState);

        Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                .renderModel(transformation.last(), consumer, blockState, model, 0, 0, 0, 0, 0);

        final var entry = new BufferEntry(consumer, Pattern.Type.BLOCK, Block.getId(blockState));
        PatternC2S.send(entry);
        return entry;
    }

    static Pattern getBlockEntity(BlockEntity blockEntity, @Nullable PoseStack transformation) {
        if (transformation == null) {
            transformation = new PoseStack();
        }

        final var consumer = new QuadConsumer();
        final var renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(blockEntity);

        if (renderer != null) {
            renderer.render(blockEntity, 0, transformation, consumer.asProvider(), 0, 0);
        }

        final var entry = new BufferEntry(consumer, Pattern.Type.BLOCK, Block.getId(blockEntity.getBlockState()));
        PatternC2S.send(entry);
        return entry;
    }

    static Pattern getEntity(Entity entity, @Nullable PoseStack transformation) {
        if (transformation == null) {
            transformation = new PoseStack();
        }

        final var consumer = new QuadConsumer();
        Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity)
                .render(entity, 0, 0, transformation, consumer.asProvider(), 0);

        final var entry = new BufferEntry(consumer, Pattern.Type.ENTITY, Registry.ENTITY_TYPE.getId(entity.getType()));
        PatternC2S.send(entry);
        return entry;
    }

    static Pattern getItem(Item item, @Nullable PoseStack transformation) {
        if (transformation == null) {
            transformation = new PoseStack();
        }

        final var consumer = new QuadConsumer();
        Minecraft.getInstance().getItemRenderer()
                .renderStatic(new ItemStack(item), ItemTransforms.TransformType.GROUND, 0, 0, transformation, consumer.asProvider(), 0);

        final var entry = new BufferEntry(consumer, Pattern.Type.ITEM, Registry.ITEM.getId(item));
        PatternC2S.send(entry);
        return entry;
    }
}