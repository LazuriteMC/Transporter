package dev.lazurite.transporter.impl.forge;

import dev.lazurite.transporter.common.impl.Transporter;
import dev.lazurite.transporter.impl.pattern.packet.forge.PatternC2SForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

@Mod(Transporter.MODID)
public class TransporterForge {
    // hmmmmmmm
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Transporter.MODID, "packet_handler"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public TransporterForge() {
        Transporter.init();
        PACKET_HANDLER.registerMessage(0, PatternC2SForge.class, PatternC2SForge::encode, PatternC2SForge::decode, PatternC2SForge::accept);
    }
}