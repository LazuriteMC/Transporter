package dev.lazurite.transporter;

import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The client and server entrypoint for Transporter. It also
 * contains the {@link PatternBuffer} used on the server.
 *
 * @see PatternBuffer
 */
@Mod(Transporter.MODID)
public class Transporter {
    public static final String MODID = "transporter";
    public static final Logger LOGGER = LogManager.getLogger("Transporter");

    private static final PatternBuffer patternBuffer = new PatternBufferImpl();


    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Highly illogical.");
        TransporterPacketHandler.registerPackets();
    }

    public static PatternBuffer getPatternBuffer() {
        return patternBuffer;
    }
}
