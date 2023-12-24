package dev.lazurite.transporter.impl.pattern.net;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import dev.lazurite.transporter.api.event.PatternBufferEvents;
import dev.lazurite.transporter.impl.TransporterMod;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.impl.pattern.BufferEntry;

public record PatternPacket(BufferEntry pattern) implements FabricPacket {

    public static final PacketType<PatternPacket> TYPE = PacketType.create(new ResourceLocation(TransporterMod.MODID, "pattern"), PatternPacket::new);

    public PatternPacket(FriendlyByteBuf buf) {
        this(BufferEntry.deserialize(buf));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(pattern.getType());
        buf.writeInt(pattern.getRegistryId());
        buf.writeInt(pattern.getQuads().size());
        pattern.getQuads().forEach(quad -> quad.serialize(buf));
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void accept(PatternPacket packet, ServerPlayer player, PacketSender sender) {
        ((PatternBufferImpl) TransporterMod.getPatternBuffer()).put(packet.pattern);
        PatternBufferEvents.BUFFER_UPDATE.invoker().onBufferUpdate(packet.pattern);
    }

}

