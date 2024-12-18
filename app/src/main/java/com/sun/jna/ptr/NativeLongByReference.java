package com.sun.jna.ptr;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
public class NativeLongByReference extends ByReference {
    public NativeLongByReference() {
        this(new NativeLong(0L));
    }

    public NativeLongByReference(NativeLong nativeLong) {
        super(NativeLong.SIZE);
        setValue(nativeLong);
    }

    public NativeLong getValue() {
        return getPointer().getNativeLong(0L);
    }

    public void setValue(NativeLong nativeLong) {
        getPointer().setNativeLong(0L, nativeLong);
    }

    @Override
    public String toString() {
        return NativeLong.SIZE > 4 ? String.format("NativeLong@0x1$%x=0x%2$x (%2$d)", Long.valueOf(Pointer.nativeValue(getPointer())), Long.valueOf(getValue().longValue())) : String.format("NativeLong@0x1$%x=0x%2$x (%2$d)", Long.valueOf(Pointer.nativeValue(getPointer())), Integer.valueOf(getValue().intValue()));
    }
}
