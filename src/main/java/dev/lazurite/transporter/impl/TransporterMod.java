package dev.lazurite.transporter.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dev.lazurite.transporter.impl.pattern.net.PatternPacket;

public class TransporterMod implements ModInitializer {

    public static final String MODID = "transporter";
    public static final Logger LOGGER = LogManager.getLogger("Transporter");
    private static PatternBuffer BUFFER;

    @Override
    public void onInitialize() {
        LOGGER.info("Beam me up, Scotty!");
        ServerPlayNetworking.registerGlobalReceiver(PatternPacket.TYPE, PatternPacket::accept);
    }

    public static PatternBuffer getPatternBuffer() {
        if (BUFFER == null) {
            BUFFER = new PatternBufferImpl();
        }
        return BUFFER;
    }

}