package dev.lazurite.transporter.fabric;

import dev.lazurite.transporter.impl.Transporter;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransporterFabric implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("Transporter");

    @Override
    public void onInitialize() {
        Transporter.initialize();
        LOGGER.info("Beam me up, Scotty!");
    }

}
