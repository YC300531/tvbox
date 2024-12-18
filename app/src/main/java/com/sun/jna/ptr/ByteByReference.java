package com.sun.jna.ptr;

import com.sun.jna.Pointer;
public class ByteByReference extends ByReference {
    public ByteByReference() {
        this((byte) 0);
    }

    public ByteByReference(byte b) {
        super(1);
        setValue(b);
    }

    public byte getValue() {
        return getPointer().getByte(0L);
    }

    public void setValue(byte b) {
        getPointer().setByte(0L, b);
    }

    @Override
    public String toString() {
        return String.format("byte@0x%1$x=0x%2$x (%2$d)", Long.valueOf(Pointer.nativeValue(getPointer())), Byte.valueOf(getValue()));
    }
}
