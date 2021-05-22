package dev.lazurite.transporter;

import dev.lazurite.transporter.impl.buffer.storage.BufferStorage;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import dev.lazurite.transporter.impl.buffer.packet.TransportPatternBufferC2S;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The client and server entrypoint for Transporter. Contains the code
 * which runs ever tick on the client that is responsible for syncing
 * {@link PatternBuffer} information to the server.
 * @see BufferStorage
 * @see PatternBuffer
 */
public class Transporter implements ModInitializer, ClientModInitializer {
    public static final String MODID = "transporter";
    public static final Logger LOGGER = LogManager.getLogger("Transporter");

    @Override
    public void onInitialize() {
        LOGGER.info("Highly illogical.");
        ServerPlayNetworking.registerGlobalReceiver(TransportPatternBufferC2S.PACKET_ID, TransportPatternBufferC2S::accept);
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_WORLD_TICK.register(world -> {
            NetworkedPatternBuffer patternBuffer = ((BufferStorage) world).getPatternBuffer();

            if (patternBuffer.isDirty()) {
                TransportPatternBufferC2S.send(patternBuffer);
            }

            patternBuffer.clear();
        });
    }
}
