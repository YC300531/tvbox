package com.sun.jna;

import java.util.Objects;
public class NativeString implements CharSequence, Comparable {
    public static final String WIDE_STRING = "--WIDE-STRING--";
    private String encoding;
    private Pointer pointer;

    public class StringMemory extends Memory {
        public StringMemory(long j) {
            super(j);
        }

        @Override
        public String toString() {
            return toString();
        }
    }

    public NativeString(WString wString) {
        this(wString.toString(), WIDE_STRING);
    }

    public NativeString(String str) {
        this(str, Native.getDefaultStringEncoding());
    }

    public NativeString(String str, String str2) {
        Objects.requireNonNull(str, "String must not be null");
        this.encoding = str2;
        if (WIDE_STRING.equals(str2)) {
            StringMemory stringMemory = new StringMemory((str.length() + 1) * Native.WCHAR_SIZE);
            this.pointer = stringMemory;
            stringMemory.setWideString(0L, str);
            return;
        }
        byte[] bytes = Native.getBytes(str, str2);
        StringMemory stringMemory2 = new StringMemory(bytes.length + 1);
        this.pointer = stringMemory2;
        stringMemory2.write(0L, bytes, 0, bytes.length);
        this.pointer.setByte(bytes.length, (byte) 0);
    }

    public NativeString(String str, boolean z) {
        this(str, z ? WIDE_STRING : Native.getDefaultStringEncoding());
    }

    @Override
    public char charAt(int i) {
        return toString().charAt(i);
    }

    @Override
    public int compareTo(Object obj) {
        if (obj == null) {
            return 1;
        }
        return toString().compareTo(obj.toString());
    }

    public boolean equals(Object obj) {
        return (obj instanceof CharSequence) && compareTo(obj) == 0;
    }

    public Pointer getPointer() {
        return this.pointer;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public int length() {
        return toString().length();
    }

    @Override
    public CharSequence subSequence(int i, int i2) {
        return toString().subSequence(i, i2);
    }

    @Override
    public String toString() {
        return WIDE_STRING.equals(this.encoding) ? this.pointer.getWideString(0L) : this.pointer.getString(0L, this.encoding);
    }
}
