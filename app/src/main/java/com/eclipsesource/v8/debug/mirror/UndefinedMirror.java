package com.eclipsesource.v8.debug.mirror;

import com.eclipsesource.v8.V8Object;
public class UndefinedMirror extends ValueMirror {
    public UndefinedMirror(V8Object v8Object) {
        super(v8Object);
    }

    @Override
    public boolean isUndefined() {
        return true;
    }

    @Override
    public String toString() {
        return "undefined";
    }
}
