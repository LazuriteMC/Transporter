package dev.lazurite.transporter.impl.compat;

import net.fabricmc.loader.api.FabricLoader;
import dev.lazurite.transporter.impl.compat.pattern.QuadConsumerProvider;
import dev.lazurite.transporter.impl.pattern.QuadConsumer;

public interface Sodium {

    static boolean isInstalled() {
        return FabricLoader.getInstance().isModLoaded("sodium");
    }

    static QuadConsumer getSodiumCompatibleConsumer() {
        return QuadConsumerProvider.createSodiumQuadConsumer();
    }

}
