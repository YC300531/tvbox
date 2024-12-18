package com.eclipsesource.v8.debug.mirror;

import com.eclipsesource.v8.V8Object;
public class ValueMirror extends Mirror {
    private static final String VALUE = "value";

    public ValueMirror(V8Object v8Object) {
        super(v8Object);
    }

    public Object getValue() {
        return this.v8Object.executeFunction(VALUE, null);
    }

    @Override
    public boolean isValue() {
        return true;
    }
}
