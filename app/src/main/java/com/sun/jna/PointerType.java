package com.sun.jna;

import androidx.base.a.b;
public abstract class PointerType implements NativeMapped {
    private Pointer pointer;

    public PointerType() {
        this.pointer = Pointer.NULL;
    }

    public PointerType(Pointer pointer) {
        this.pointer = pointer;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PointerType) {
            Pointer pointer = ((PointerType) obj).getPointer();
            Pointer pointer2 = this.pointer;
            return pointer2 == null ? pointer == null : pointer2.equals(pointer);
        }
        return false;
    }

    @Override
    public Object fromNative(Object obj, FromNativeContext fromNativeContext) {
        if (obj == null) {
            return null;
        }
        PointerType pointerType = (PointerType) Klass.newInstance(getClass());
        pointerType.pointer = (Pointer) obj;
        return pointerType;
    }

    public Pointer getPointer() {
        return this.pointer;
    }

    public int hashCode() {
        Pointer pointer = this.pointer;
        if (pointer != null) {
            return pointer.hashCode();
        }
        return 0;
    }

    @Override
    public Class<?> nativeType() {
        return Pointer.class;
    }

    public void setPointer(Pointer pointer) {
        this.pointer = pointer;
    }

    @Override
    public Object toNative() {
        return getPointer();
    }

    public String toString() {
        if (this.pointer == null) {
            return "NULL";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.pointer.toString());
        sb.append(" (");
        return b.c(sb, super.toString(), ")");
    }
}
