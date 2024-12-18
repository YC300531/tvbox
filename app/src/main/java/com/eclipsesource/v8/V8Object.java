package com.eclipsesource.v8;

import java.lang.reflect.Method;
public class V8Object extends V8Value {

    public static class Undefined extends V8Object {
        @Override
        public V8Object add(String str, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object add(String str, int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object add(String str, V8Value v8Value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object add(String str, String str2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object add(String str, boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object addUndefined(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() {
        }

        @Override
        public boolean contains(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof V8Object) && ((V8Object) obj).isUndefined();
        }

        @Override
        public V8Array executeArrayFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean executeBooleanFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double executeDoubleFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object executeFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int executeIntegerFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object executeJSFunction(String str, Object... objArr) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object executeObjectFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String executeStringFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void executeVoidFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Array getArray(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean getBoolean(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double getDouble(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getInteger(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String[] getKeys() {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object getObject(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8 getRuntime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getString(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getType(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int hashCode() {
            return 919;
        }

        @Override
        public boolean isReleased() {
            return false;
        }

        @Override
        public boolean isUndefined() {
            return true;
        }

        @Override
        public V8Object registerJavaMethod(JavaCallback javaCallback, String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object registerJavaMethod(JavaVoidCallback javaVoidCallback, String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object registerJavaMethod(Object obj, String str, String str2, Class<?>[] clsArr, boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void release() {
        }

        @Override
        public V8Object setPrototype(V8Object v8Object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return "undefined";
        }

        @Override
        public Undefined twin() {
            return (Undefined) super.twin();
        }
    }

    public V8Object() {
    }

    public V8Object(V8 v8) {
        this(v8, null);
    }

    public V8Object(V8 v8, Object obj) {
        super(v8);
        if (v8 != null) {
            this.v8.checkThread();
            initialize(this.v8.getV8RuntimePtr(), obj);
        }
    }

    private void checkKey(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
    }

    public V8Object add(String str, double d) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.add(v8.getV8RuntimePtr(), this.objectHandle, str, d);
        return this;
    }

    public V8Object add(String str, int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.add(v8.getV8RuntimePtr(), this.objectHandle, str, i);
        return this;
    }

    public V8Object add(String str, V8Value v8Value) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Value);
        if (v8Value == null) {
            V8 v8 = this.v8;
            v8.addNull(v8.getV8RuntimePtr(), this.objectHandle, str);
        } else if (v8Value.equals(V8.getUndefined())) {
            V8 v82 = this.v8;
            v82.addUndefined(v82.getV8RuntimePtr(), this.objectHandle, str);
        } else {
            V8 v83 = this.v8;
            v83.addObject(v83.getV8RuntimePtr(), this.objectHandle, str, v8Value.getHandle());
        }
        return this;
    }

    public V8Object add(String str, String str2) {
        this.v8.checkThread();
        checkReleased();
        if (str2 == null) {
            V8 v8 = this.v8;
            v8.addNull(v8.getV8RuntimePtr(), this.objectHandle, str);
        } else if (str2.equals(V8.getUndefined())) {
            V8 v82 = this.v8;
            v82.addUndefined(v82.getV8RuntimePtr(), this.objectHandle, str);
        } else {
            V8 v83 = this.v8;
            v83.add(v83.getV8RuntimePtr(), this.objectHandle, str, str2);
        }
        return this;
    }

    public V8Object add(String str, boolean z) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.add(v8.getV8RuntimePtr(), this.objectHandle, str, z);
        return this;
    }

    public V8Object addNull(String str) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.addNull(v8.getV8RuntimePtr(), this.objectHandle, str);
        return this;
    }

    public V8Object addUndefined(String str) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.addUndefined(v8.getV8RuntimePtr(), this.objectHandle, str);
        return this;
    }

    public boolean contains(String str) {
        this.v8.checkThread();
        checkReleased();
        checkKey(str);
        V8 v8 = this.v8;
        return v8.contains(v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    @Override
    public V8Value createTwin() {
        return new V8Object(this.v8);
    }

    public V8Array executeArrayFunction(String str, V8Array v8Array) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        V8 v8 = this.v8;
        Object executeFunction = v8.executeFunction(v8.getV8RuntimePtr(), 5, this.objectHandle, str, handle);
        if (executeFunction instanceof V8Array) {
            return (V8Array) executeFunction;
        }
        throw new V8ResultUndefined();
    }

    public boolean executeBooleanFunction(String str, V8Array v8Array) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        V8 v8 = this.v8;
        return v8.executeBooleanFunction(v8.getV8RuntimePtr(), getHandle(), str, handle);
    }

    public double executeDoubleFunction(String str, V8Array v8Array) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        V8 v8 = this.v8;
        return v8.executeDoubleFunction(v8.getV8RuntimePtr(), getHandle(), str, handle);
    }

    public Object executeFunction(String str, V8Array v8Array) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        V8 v8 = this.v8;
        return v8.executeFunction(v8.getV8RuntimePtr(), 0, this.objectHandle, str, handle);
    }

    public int executeIntegerFunction(String str, V8Array v8Array) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        V8 v8 = this.v8;
        return v8.executeIntegerFunction(v8.getV8RuntimePtr(), getHandle(), str, handle);
    }

    public Object executeJSFunction(String str) {
        return executeFunction(str, null);
    }

    public Object executeJSFunction(String str, Object... objArr) {
        if (objArr == null) {
            return executeFunction(str, null);
        }
        V8Array v8Array = new V8Array(this.v8.getRuntime());
        try {
            for (Object obj : objArr) {
                if (obj == null) {
                    v8Array.pushNull();
                } else if (obj instanceof V8Value) {
                    v8Array.push((V8Value) obj);
                } else {
                    if (!(obj instanceof Integer) && !(obj instanceof Double)) {
                        if (obj instanceof Long) {
                            v8Array.push(((Long) obj).doubleValue());
                        } else if (obj instanceof Float) {
                            v8Array.push(((Float) obj).floatValue());
                        } else if (!(obj instanceof Boolean)) {
                            if (!(obj instanceof String)) {
                                throw new IllegalArgumentException("Unsupported Object of type: " + obj.getClass());
                            }
                            v8Array.push((String) obj);
                        }
                    }
                    v8Array.push(obj);
                }
            }
            return executeFunction(str, v8Array);
        } finally {
            v8Array.close();
        }
    }

    public V8Object executeObjectFunction(String str, V8Array v8Array) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        V8 v8 = this.v8;
        Object executeFunction = v8.executeFunction(v8.getV8RuntimePtr(), 6, this.objectHandle, str, handle);
        if (executeFunction instanceof V8Object) {
            return (V8Object) executeFunction;
        }
        throw new V8ResultUndefined();
    }

    public String executeStringFunction(String str, V8Array v8Array) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        V8 v8 = this.v8;
        return v8.executeStringFunction(v8.getV8RuntimePtr(), getHandle(), str, handle);
    }

    public void executeVoidFunction(String str, V8Array v8Array) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        V8 v8 = this.v8;
        v8.executeVoidFunction(v8.getV8RuntimePtr(), this.objectHandle, str, handle);
    }

    public Object get(String str) {
        this.v8.checkThread();
        checkReleased();
        checkKey(str);
        V8 v8 = this.v8;
        return v8.get(v8.getV8RuntimePtr(), 6, this.objectHandle, str);
    }

    public V8Array getArray(String str) {
        this.v8.checkThread();
        checkReleased();
        checkKey(str);
        V8 v8 = this.v8;
        Object obj = v8.get(v8.getV8RuntimePtr(), 5, this.objectHandle, str);
        if (obj == null || (obj instanceof V8Array)) {
            return (V8Array) obj;
        }
        throw new V8ResultUndefined();
    }

    public boolean getBoolean(String str) {
        this.v8.checkThread();
        checkReleased();
        checkKey(str);
        V8 v8 = this.v8;
        return v8.getBoolean(v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public double getDouble(String str) {
        this.v8.checkThread();
        checkReleased();
        checkKey(str);
        V8 v8 = this.v8;
        return v8.getDouble(v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public int getInteger(String str) {
        this.v8.checkThread();
        checkReleased();
        checkKey(str);
        V8 v8 = this.v8;
        return v8.getInteger(v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public String[] getKeys() {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.getKeys(v8.getV8RuntimePtr(), this.objectHandle);
    }

    public V8Object getObject(String str) {
        this.v8.checkThread();
        checkReleased();
        checkKey(str);
        V8 v8 = this.v8;
        Object obj = v8.get(v8.getV8RuntimePtr(), 6, this.objectHandle, str);
        if (obj == null || (obj instanceof V8Object)) {
            return (V8Object) obj;
        }
        throw new V8ResultUndefined();
    }

    public String getString(String str) {
        this.v8.checkThread();
        checkReleased();
        checkKey(str);
        V8 v8 = this.v8;
        return v8.getString(v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public int getType(String str) {
        this.v8.checkThread();
        checkReleased();
        checkKey(str);
        V8 v8 = this.v8;
        return v8.getType(v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public V8Object registerJavaMethod(JavaCallback javaCallback, String str) {
        this.v8.checkThread();
        checkReleased();
        this.v8.registerCallback(javaCallback, getHandle(), str);
        return this;
    }

    public V8Object registerJavaMethod(JavaVoidCallback javaVoidCallback, String str) {
        this.v8.checkThread();
        checkReleased();
        this.v8.registerVoidCallback(javaVoidCallback, getHandle(), str);
        return this;
    }

    public V8Object registerJavaMethod(Object obj, String str, String str2, Class<?>[] clsArr) {
        return registerJavaMethod(obj, str, str2, clsArr, false);
    }

    public V8Object registerJavaMethod(Object obj, String str, String str2, Class<?>[] clsArr, boolean z) {
        this.v8.checkThread();
        checkReleased();
        try {
            Method method = obj.getClass().getMethod(str, clsArr);
            method.setAccessible(true);
            this.v8.registerCallback(obj, method, getHandle(), str2, z);
            return this;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        } catch (SecurityException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public V8Object setPrototype(V8Object v8Object) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.setPrototype(v8.getV8RuntimePtr(), this.objectHandle, v8Object.getHandle());
        return this;
    }

    public String toString() {
        if (isReleased() || this.v8.isReleased()) {
            return "[Object released]";
        }
        this.v8.checkThread();
        V8 v8 = this.v8;
        return v8.toString(v8.getV8RuntimePtr(), getHandle());
    }

    @Override
    public V8Object twin() {
        return (V8Object) super.twin();
    }
}
