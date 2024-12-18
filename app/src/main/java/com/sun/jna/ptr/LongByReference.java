package com.sun.jna.ptr;

import com.sun.jna.Pointer;
public class LongByReference extends ByReference {
    public LongByReference() {
        this(0L);
    }

    public LongByReference(long j) {
        super(8);
        setValue(j);
    }

    public long getValue() {
        return getPointer().getLong(0L);
    }

    public void setValue(long j) {
        getPointer().setLong(0L, j);
    }

    @Override
    public String toString() {
        return String.format("long@0x%1$x=0x%2$x (%2$d)", Long.valueOf(Pointer.nativeValue(getPointer())), Long.valueOf(getValue()));
    }
}
