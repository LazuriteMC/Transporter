package dev.lazurite.transporter.impl.forge;

import dev.lazurite.transporter.impl.Transporter;
import dev.lazurite.transporter.impl.pattern.packet.forge.PatternC2SImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(Transporter.MODID)
public class TransporterForge {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Transporter.MODID, "packet_handler"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public TransporterForge() {
        Transporter.init();
        PACKET_HANDLER.registerMessage(0, PatternC2SImpl.class, PatternC2SImpl::encode, PatternC2SImpl::decode, PatternC2SImpl::accept);
    }
}