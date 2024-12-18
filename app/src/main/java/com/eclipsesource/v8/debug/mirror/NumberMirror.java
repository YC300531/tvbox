package com.eclipsesource.v8.debug.mirror;

import com.eclipsesource.v8.V8Object;
public class NumberMirror extends ValueMirror {
    public NumberMirror(V8Object v8Object) {
        super(v8Object);
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public String toString() {
        return this.v8Object.executeStringFunction("toText", null);
    }
}
