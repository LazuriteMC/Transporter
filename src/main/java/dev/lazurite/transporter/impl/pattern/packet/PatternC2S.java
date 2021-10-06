package dev.lazurite.transporter.impl.pattern.packet;

import dev.lazurite.transporter.Transporter;
import dev.lazurite.transporter.TransporterPacketHandler;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.event.PatternBufferEvents;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.model.Quad;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * The packet responsible for transporting a given {@link Pattern} to the server.
 *
 * @see PatternBuffer
 */
public class PatternC2S {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(Transporter.MODID, "pattern_c2s");
    private final BufferEntry pattern;

    public PatternC2S(BufferEntry pattern) {
        this.pattern = pattern;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(pattern.getResourceLocation());
        buf.writeInt(pattern.getQuads().size());
        for (var quad : pattern.getQuads()) {
            quad.serialize(buf);
        }
    }

    public static PatternC2S decode(FriendlyByteBuf buf) {
        var quads = new ArrayList<Quad>();
        var identifier = buf.readResourceLocation();
        var quadCount = buf.readInt();

        for (var j = 0; j < quadCount; j++) {
            quads.add(Quad.deserialize(buf));
        }

        return new PatternC2S(new BufferEntry(quads, identifier));
    }

    public static void handle(PatternC2S message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var quads = message.pattern.getQuads();
            var identifier = message.pattern.getResourceLocation();
            var buffer = (PatternBufferImpl) Transporter.getPatternBuffer();
            buffer.put(new BufferEntry(quads, identifier));
            MinecraftForge.EVENT_BUS.post(new PatternBufferEvents(buffer));
        });
    }

    public static void send(BufferEntry pattern) {
        TransporterPacketHandler.PACKET_HANDLER.sendToServer(new PatternC2S(pattern));
    }
}