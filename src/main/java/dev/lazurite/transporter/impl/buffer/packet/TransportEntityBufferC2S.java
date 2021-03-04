package dev.lazurite.transporter.impl.buffer.packet;

import com.google.common.collect.Lists;
import dev.lazurite.transporter.Transporter;
import dev.lazurite.transporter.api.buffer.BufferStorage;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.api.event.PatternBufferEvents;
import dev.lazurite.transporter.api.pattern.TypedPattern;
import dev.lazurite.transporter.impl.buffer.NetworkedPatternBuffer;
import dev.lazurite.transporter.impl.pattern.BufferEntry;
import dev.lazurite.transporter.impl.pattern.part.Quad;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * The packet responsible for syncing the entity buffer from the client to the server.
 * @see Transporter#onInitializeClient()
 * @see PatternBuffer
 * @see BufferStorage
 */
public class TransportEntityBufferC2S {
    public static final Identifier PACKET_ID = new Identifier(Transporter.MODID, "transport_entity_buffer_c2s");

    public static void accept(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        List<BufferEntry<Entity>> patterns = Lists.newArrayList();
        int patternCount = buf.readInt();

        for (int i = 0; i < patternCount; i++) {
            List<Quad> quads = Lists.newArrayList();
            Entity entity = player.getEntityWorld().getEntityById(buf.readInt());
            int quadCount = buf.readInt();

            for (int j = 0; j < quadCount; j++) {
                quads.add(Quad.deserialize(buf));
            }

            patterns.add(new BufferEntry<>(quads, entity));
        }

        server.execute(() -> {
            NetworkedPatternBuffer<Entity> buffer = ((BufferStorage) player.getEntityWorld()).getEntityBuffer();
            patterns.forEach(buffer::put);
            PatternBufferEvents.ENTITY_BUFFER_UPDATE.invoker().onUpdate(buffer);
        });
    }

    public static void send(NetworkedPatternBuffer<Entity> buffer) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(buffer.size());
        System.out.println("SEND ENTITY");

        for (TypedPattern<Entity> pattern : buffer.getAll().values()) {
            buf.writeInt(pattern.getIdentifier().getEntityId());
            buf.writeInt(pattern.getQuads().size());

            for (Quad quad : pattern.getQuads()) {
                quad.serialize(buf);
            }
        }

        buffer.setDirty(false);
        ClientPlayNetworking.send(PACKET_ID, buf);
    }
}
