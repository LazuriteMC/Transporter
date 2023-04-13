package dev.lazurite.transporter.forge;

import dev.lazurite.transporter.impl.Transporter;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("transporter")
public class TransporterForge {

    public static final Logger LOGGER = LogManager.getLogger("Transporter");

    public TransporterForge() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @SubscribeEvent
    public void onInitializeClient(FMLClientSetupEvent event) {
        Transporter.initialize();
        LOGGER.info("Beam me up, Scotty!");
    }

}
