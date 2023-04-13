package dev.lazurite.transporter.impl;

import dev.lazurite.toolbox.api.network.PacketRegistry;
import dev.lazurite.transporter.api.buffer.PatternBuffer;
import dev.lazurite.transporter.impl.buffer.PatternBufferImpl;
import dev.lazurite.transporter.impl.pattern.packet.PatternC2S;

public class Transporter {
    private static final PatternBuffer patternBuffer = new PatternBufferImpl();

    public static void initialize() {
        PacketRegistry.registerServerbound(PatternC2S.IDENTIFIER, context -> PatternC2S.accept(context.byteBuf()));
    }

    public static PatternBuffer getPatternBuffer() {
        return patternBuffer;
    }
}