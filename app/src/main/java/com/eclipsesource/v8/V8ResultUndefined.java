package com.eclipsesource.v8;
public class V8ResultUndefined extends V8RuntimeException {
    public V8ResultUndefined() {
    }

    public V8ResultUndefined(String str) {
        super(str);
    }
}
