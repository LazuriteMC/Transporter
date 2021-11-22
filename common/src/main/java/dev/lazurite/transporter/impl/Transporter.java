package dev.lazurite.transporter.impl;

import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
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
    }

    public static PatternBuffer getPatternBuffer() {
        return patternBuffer;
    }
}
