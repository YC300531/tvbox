package com.sun.jna.ptr;

import com.sun.jna.Pointer;
public class FloatByReference extends ByReference {
    public FloatByReference() {
        this(0.0f);
    }

    public FloatByReference(float f) {
        super(4);
        setValue(f);
    }

    public float getValue() {
        return getPointer().getFloat(0L);
    }

    public void setValue(float f) {
        getPointer().setFloat(0L, f);
    }

    @Override
    public String toString() {
        return String.format("float@0x%x=%s", Long.valueOf(Pointer.nativeValue(getPointer())), Float.valueOf(getValue()));
    }
}
