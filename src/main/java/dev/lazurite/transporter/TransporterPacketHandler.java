package dev.lazurite.transporter;

import dev.lazurite.transporter.impl.pattern.packet.PatternC2S;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class TransporterPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Transporter.MODID, "packet_handler"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        PACKET_HANDLER.registerMessage(0, PatternC2S.class, PatternC2S::encode, PatternC2S::decode, PatternC2S::handle);
    }
}
