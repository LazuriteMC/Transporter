package dev.lazurite.transporter.impl.pattern.packet.forge;

import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.packet.PatternC2S;
import dev.lazurite.transporter.impl.forge.TransporterForge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.LogicalSidedProvider;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PatternC2SImpl {
    private final BufferEntry pattern;

    public PatternC2SImpl(BufferEntry pattern) {
        this.pattern = pattern;
    }

    public BufferEntry getPattern() {
        return this.pattern;
    }

    public static void send(BufferEntry pattern) {
        TransporterForge.PACKET_HANDLER.sendToServer(new PatternC2SImpl(pattern));
    }

    public void encode(FriendlyByteBuf buf) {
        PatternC2S.encode(pattern, buf);
    }

    public static PatternC2SImpl decode(FriendlyByteBuf buf) {
        return new PatternC2SImpl(PatternC2S.decode(buf));
    }

    public static void accept(PatternC2SImpl message, Supplier<NetworkEvent.Context> context) {
        PatternC2S.accept(message.getPattern(), LogicalSidedProvider.WORKQUEUE.get(context.get().getDirection().getReceptionSide()));
    }
}