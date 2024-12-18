package com.sun.jna.ptr;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
public abstract class ByReference extends PointerType {
    public ByReference(int i) {
        setPointer(new Memory(i));
    }

    @Override
    public String toString() {
        try {
            Object invoke = getClass().getMethod("getValue", new Class[0]).invoke(this, new Object[0]);
            return invoke == null ? String.format("null@0x%x", Long.valueOf(Pointer.nativeValue(getPointer()))) : String.format("%s@0x%x=%s", invoke.getClass().getSimpleName(), Long.valueOf(Pointer.nativeValue(getPointer())), invoke);
        } catch (Exception e) {
            return String.format("ByReference Contract violated - %s#getValue raised exception: %s", getClass().getName(), e.getMessage());
        }
    }
}
