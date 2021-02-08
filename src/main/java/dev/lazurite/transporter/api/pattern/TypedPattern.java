package dev.lazurite.transporter.api.pattern;

public interface TypedPattern<T> extends Pattern {
    T getIdentifier();
}
