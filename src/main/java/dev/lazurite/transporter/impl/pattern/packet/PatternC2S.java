package dev.lazurite.transporter.impl.pattern.packet;

import dev.lazurite.transporter.Transporter;
import dev.lazurite.transporter.TransporterPacketHandler;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.event.PatternBufferEvents;
import dev.lazurite.transporter.api.pattern.Pattern;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.model.Quad;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The packet responsible for transporting a given {@link Pattern} to the server.
 *
 * @see PatternBuffer
 */
public class PatternC2S {
    private final ResourceLocation resourceLocation;
    private final List<Quad> quads;
    private final int intDirection;

    public PatternC2S(ResourceLocation resourceLocation, List<Quad> quads, int intDirection) {
        this.resourceLocation = resourceLocation;
        this.quads = quads;
        this.intDirection = intDirection;
    }

    public PatternC2S(BufferEntry entry) {
        this(entry.getResourceLocation(), entry.getQuads(),
                entry.getDirection().isPresent() ? entry.getDirection().get().get2DDataValue() : -1);
    }

    public static PatternC2S decode(FriendlyByteBuf buf) {
        final var quads = new ArrayList<Quad>();
        final var identifier = buf.readResourceLocation();
        final var quadCount = buf.readInt();
        final var intDirection = buf.readInt();

        for (var j = 0; j < quadCount; j++) {
            quads.add(Quad.deserialize(buf));
        }

        return new PatternC2S(identifier, quads, intDirection);
    }

    public static void handle(PatternC2S message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var quads = message.getQuads();
            var identifier = message.getResourceLocation();
            final var buffer = (PatternBufferImpl) Transporter.getPatternBuffer();
            buffer.put(
                    new BufferEntry(quads, identifier,
                            message.getIntDirection() == -1 ? null : Direction.from2DDataValue(message.intDirection)));
            buffer.put(new BufferEntry(quads, identifier));
            MinecraftForge.EVENT_BUS.post(new PatternBufferEvents(buffer));
        });
    }

    public static void send(BufferEntry pattern) {
        TransporterPacketHandler.PACKET_HANDLER.sendToServer(new PatternC2S(pattern));
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public List<Quad> getQuads() {
        return quads;
    }

    public int getIntDirection() {
        return intDirection;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(resourceLocation);
        buf.writeInt(quads.size());
        buf.writeInt(intDirection);
        for (var quad : quads) {
            quad.serialize(buf);
        }
    }
}