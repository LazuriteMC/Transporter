package dev.lazurite.transporter.impl.buffer;

import com.google.common.collect.Lists;
import dev.lazurite.transporter.api.pattern.TypedPattern;

import java.util.List;

public class NetworkedPatternBuffer<T> extends AbstractPatternBuffer<T> {
    private boolean dirty;

    @Override
    public boolean put(TypedPattern<T> pattern) {
        return setDirty(super.put(pattern));
    }

    public List<TypedPattern<T>> getAll() {
        return Lists.newArrayList(patterns);
    }

    public void clear() {
        patterns.clear();
    }

    public boolean setDirty(boolean dirty) {
        this.dirty = dirty;
        return this.dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
}

