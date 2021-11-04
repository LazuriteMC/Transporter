package dev.lazurite.transporter.fabric;

import dev.lazurite.transporter.common.impl.Transporter;
import net.fabricmc.api.ModInitializer;

public class TransporterFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Transporter.init();
    }
}
