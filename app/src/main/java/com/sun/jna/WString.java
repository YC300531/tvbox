package com.sun.jna;

import java.util.Objects;
public final class WString implements CharSequence, Comparable {
    private String string;

    public WString(String str) {
        Objects.requireNonNull(str, "String initializer must be non-null");
        this.string = str;
    }

    @Override
    public char charAt(int i) {
        return toString().charAt(i);
    }

    @Override
    public int compareTo(Object obj) {
        return toString().compareTo(obj.toString());
    }

    public boolean equals(Object obj) {
        return (obj instanceof WString) && toString().equals(obj.toString());
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
        return this.string;
    }
}
