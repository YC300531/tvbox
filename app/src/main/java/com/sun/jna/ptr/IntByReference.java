package com.sun.jna.ptr;

import com.sun.jna.Pointer;
public class IntByReference extends ByReference {
    public IntByReference() {
        this(0);
    }

    public IntByReference(int i) {
        super(4);
        setValue(i);
    }

    public int getValue() {
        return getPointer().getInt(0L);
    }

    public void setValue(int i) {
        getPointer().setInt(0L, i);
    }

    @Override
    public String toString() {
        return String.format("int@0x%1$x=0x%2$x (%2$d)", Long.valueOf(Pointer.nativeValue(getPointer())), Integer.valueOf(getValue()));
    }
}
