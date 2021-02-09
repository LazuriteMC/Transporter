package dev.lazurite.transporter.api.pattern;

import dev.lazurite.transporter.impl.pattern.part.Quad;

import java.util.List;

/**
 * A basic pattern contains just a set of quads.
 * @see TypedPattern
 */
public interface Pattern {
    List<Quad> getQuads();
}
