package dev.lazurite.transporter;

import dev.lazurite.transporter.api.buffer.BufferStorage;
import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import dev.lazurite.transporter.impl.buffer.packet.TransportBlockBufferC2S;
import dev.lazurite.transporter.impl.buffer.packet.TransportEntityBufferC2S;
import dev.lazurite.transporter.impl.buffer.packet.TransportItemBufferC2S;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Transporter implements ModInitializer, ClientModInitializer {
    public static final String MODID = "transporter";
    public static final Logger LOGGER = LogManager.getLogger("Transporter");

    @Override
    public void onInitialize() {
        LOGGER.info("That's the trouble with tribbles...");

        ServerPlayNetworking.registerGlobalReceiver(TransportBlockBufferC2S.PACKET_ID, TransportBlockBufferC2S::accept);
        ServerPlayNetworking.registerGlobalReceiver(TransportEntityBufferC2S.PACKET_ID, TransportEntityBufferC2S::accept);
        ServerPlayNetworking.registerGlobalReceiver(TransportItemBufferC2S.PACKET_ID, TransportItemBufferC2S::accept);

//        ServerTickEvents.START_WORLD_TICK.register(world -> {
//            ((BufferStorage) world).getBlockBuffer().clear();
//            ((BufferStorage) world).getEntityBuffer().clear();
//            ((BufferStorage) world).getItemBuffer().clear();
//        });
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_WORLD_TICK.register(world -> {
            NetworkedPatternBuffer<BlockPos> blockBuffer = ((BufferStorage) world).getBlockBuffer();
            NetworkedPatternBuffer<Entity> entityBuffer = ((BufferStorage) world).getEntityBuffer();
            NetworkedPatternBuffer<Item> itemBuffer = ((BufferStorage) world).getItemBuffer();

            if (blockBuffer.isDirty()) TransportBlockBufferC2S.send(blockBuffer);
            if (entityBuffer.isDirty()) TransportEntityBufferC2S.send(entityBuffer);
            if (itemBuffer.isDirty()) TransportItemBufferC2S.send(itemBuffer);

            blockBuffer.clear();
            entityBuffer.clear();
            itemBuffer.clear();
        });
    }
}
