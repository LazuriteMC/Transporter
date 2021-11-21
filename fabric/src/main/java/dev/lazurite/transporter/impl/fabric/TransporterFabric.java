package dev.lazurite.transporter.impl.fabric;

import dev.lazurite.transporter.common.impl.Transporter;
import dev.lazurite.transporter.common.impl.pattern.packet.PatternC2S;
import dev.lazurite.transporter.impl.pattern.packet.fabric.PatternC2SFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class TransporterFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Transporter.init();
        ServerPlayNetworking.registerGlobalReceiver(PatternC2SFabric.PACKET_ID, (server, player, handler, buf, responseSender) -> PatternC2S.accept(PatternC2S.decode(buf), server));
    }
}