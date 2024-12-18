package com.eclipsesource.v8;
public class V8Function extends V8Object {
    public V8Function(V8 v8) {
        this(v8, null);
    }

    public V8Function(V8 v8, JavaCallback javaCallback) {
        super(v8, javaCallback);
    }

    public Object call(V8Object v8Object, V8Array v8Array) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Object);
        this.v8.checkRuntime(v8Array);
        if (v8Object == null) {
            v8Object = this.v8;
        }
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        if (v8Object.isUndefined()) {
            v8Object = this.v8;
        }
        long handle2 = v8Object.getHandle();
        V8 v8 = this.v8;
        return v8.executeFunction(v8.getV8RuntimePtr(), handle2, this.objectHandle, handle);
    }

    @Override
    public V8Value createTwin() {
        return new V8Function(this.v8);
    }

    @Override
    public void initialize(long j, Object obj) {
        if (obj == null) {
            super.initialize(j, null);
            return;
        }
        long[] initNewV8Function = this.v8.initNewV8Function(j);
        this.v8.createAndRegisterMethodDescriptor((JavaCallback) obj, initNewV8Function[1]);
        this.released = false;
        addObjectReference(initNewV8Function[0]);
    }

    @Override
    public String toString() {
        return (this.released || this.v8.isReleased()) ? "[Function released]" : super.toString();
    }

    @Override
    public V8Function twin() {
        return (V8Function) super.twin();
    }
}
