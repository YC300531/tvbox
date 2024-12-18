package com.eclipsesource.v8.debug.mirror;

import com.eclipsesource.v8.V8Object;
public class PropertyMirror extends Mirror {
    public PropertyMirror(V8Object v8Object) {
        super(v8Object);
    }

    public String getName() {
        return this.v8Object.executeStringFunction("name", null);
    }

    public Mirror getValue() {
        V8Object executeObjectFunction = this.v8Object.executeObjectFunction("value", null);
        try {
            return Mirror.createMirror(executeObjectFunction);
        } finally {
            executeObjectFunction.close();
        }
    }

    @Override
    public boolean isProperty() {
        return true;
    }
}
