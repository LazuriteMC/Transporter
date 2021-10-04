package dev.lazurite.transporter;

import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.pattern.packet.PatternC2S;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The client and server entrypoint for Transporter. It also
 * contains the {@link PatternBuffer} used on the server.
 * @see PatternBuffer
 */
public class Transporter implements ModInitializer {
    public static final String MODID = "transporter";
    public static final Logger LOGGER = LogManager.getLogger("Transporter");

    private static final PatternBuffer patternBuffer = new PatternBufferImpl();

    @Override
    public void onInitialize() {
        LOGGER.info("Highly illogical.");
        ServerPlayNetworking.registerGlobalReceiver(PatternC2S.PACKET_ID, PatternC2S::accept);
    }

    public static PatternBuffer getPatternBuffer() {
        return patternBuffer;
    }
}
