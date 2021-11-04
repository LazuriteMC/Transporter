package dev.lazurite.transporter.common.impl;

import dev.architectury.networking.NetworkManager;
import dev.lazurite.transporter.common.api.buffer.PatternBuffer;
import dev.lazurite.transporter.common.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.common.impl.pattern.packet.PatternC2S;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The client and server entrypoint for Transporter. It also
 * contains the {@link PatternBuffer} used on the server.
 *
 * @see PatternBuffer
 */
public class Transporter {
    public static final String MODID = "transporter";
    public static final Logger LOGGER = LogManager.getLogger("Transporter");

    private static final PatternBuffer patternBuffer = new PatternBufferImpl();

    public static void init() {
        LOGGER.info("Highly illogical.");
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, PatternC2S.PACKET_ID, PatternC2S::accept);
    }

    public static PatternBuffer getPatternBuffer() {
        return patternBuffer;
    }
}
