package com.eclipsesource.v8;
public class V8Array extends V8Object {

    public static class Undefined extends V8Array {
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
        public int executeIntegerFunction(String str, V8Array v8Array) {
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
        public Object get(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Array getArray(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Array getArray(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean getBoolean(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean getBoolean(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getBooleans(int i, int i2, boolean[] zArr) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean[] getBooleans(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte getByte(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getBytes(int i, int i2, byte[] bArr) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte[] getBytes(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double getDouble(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double getDouble(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getDoubles(int i, int i2, double[] dArr) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double[] getDoubles(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getInteger(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getInteger(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getIntegers(int i, int i2, int[] iArr) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int[] getIntegers(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String[] getKeys() {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Object getObject(int i) {
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
        public String getString(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getString(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getStrings(int i, int i2, String[] strArr) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String[] getStrings(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getType() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getType(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getType(int i, int i2) {
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
        public int length() {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Array push(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Array push(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Array push(V8Value v8Value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Array push(String str) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Array push(boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V8Array pushUndefined() {
            throw new UnsupportedOperationException();
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

    public V8Array() {
    }

    public V8Array(V8 v8) {
        super(v8);
        v8.checkThread();
    }

    public V8Array(V8 v8, Object obj) {
        super(v8, obj);
    }

    @Override
    public V8Value createTwin() {
        return new V8Array(this.v8);
    }

    public Object get(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGet(v8.getV8RuntimePtr(), 6, this.objectHandle, i);
    }

    public V8Array getArray(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        Object arrayGet = v8.arrayGet(v8.getV8RuntimePtr(), 5, this.objectHandle, i);
        if (arrayGet == null || (arrayGet instanceof V8Array)) {
            return (V8Array) arrayGet;
        }
        throw new V8ResultUndefined();
    }

    public boolean getBoolean(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetBoolean(v8.getV8RuntimePtr(), getHandle(), i);
    }

    public int getBooleans(int i, int i2, boolean[] zArr) {
        this.v8.checkThread();
        checkReleased();
        if (i2 <= zArr.length) {
            V8 v8 = this.v8;
            return v8.arrayGetBooleans(v8.getV8RuntimePtr(), getHandle(), i, i2, zArr);
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean[] getBooleans(int i, int i2) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetBooleans(v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public byte getByte(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetByte(v8.getV8RuntimePtr(), getHandle(), i);
    }

    public int getBytes(int i, int i2, byte[] bArr) {
        this.v8.checkThread();
        checkReleased();
        if (i2 <= bArr.length) {
            V8 v8 = this.v8;
            return v8.arrayGetBytes(v8.getV8RuntimePtr(), getHandle(), i, i2, bArr);
        }
        throw new IndexOutOfBoundsException();
    }

    public byte[] getBytes(int i, int i2) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetBytes(v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public double getDouble(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetDouble(v8.getV8RuntimePtr(), getHandle(), i);
    }

    public int getDoubles(int i, int i2, double[] dArr) {
        this.v8.checkThread();
        checkReleased();
        if (i2 <= dArr.length) {
            V8 v8 = this.v8;
            return v8.arrayGetDoubles(v8.getV8RuntimePtr(), getHandle(), i, i2, dArr);
        }
        throw new IndexOutOfBoundsException();
    }

    public double[] getDoubles(int i, int i2) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetDoubles(v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public int getInteger(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetInteger(v8.getV8RuntimePtr(), getHandle(), i);
    }

    public int getIntegers(int i, int i2, int[] iArr) {
        this.v8.checkThread();
        checkReleased();
        if (i2 <= iArr.length) {
            V8 v8 = this.v8;
            return v8.arrayGetIntegers(v8.getV8RuntimePtr(), getHandle(), i, i2, iArr);
        }
        throw new IndexOutOfBoundsException();
    }

    public int[] getIntegers(int i, int i2) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetIntegers(v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public V8Object getObject(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        Object arrayGet = v8.arrayGet(v8.getV8RuntimePtr(), 6, this.objectHandle, i);
        if (arrayGet == null || (arrayGet instanceof V8Object)) {
            return (V8Object) arrayGet;
        }
        throw new V8ResultUndefined();
    }

    public String getString(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetString(v8.getV8RuntimePtr(), getHandle(), i);
    }

    public int getStrings(int i, int i2, String[] strArr) {
        this.v8.checkThread();
        checkReleased();
        if (i2 <= strArr.length) {
            V8 v8 = this.v8;
            return v8.arrayGetStrings(v8.getV8RuntimePtr(), getHandle(), i, i2, strArr);
        }
        throw new IndexOutOfBoundsException();
    }

    public String[] getStrings(int i, int i2) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetStrings(v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public int getType() {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.getArrayType(v8.getV8RuntimePtr(), getHandle());
    }

    public int getType(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.getType(v8.getV8RuntimePtr(), getHandle(), i);
    }

    public int getType(int i, int i2) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.getType(v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    @Override
    public void initialize(long j, Object obj) {
        long initNewV8Array = this.v8.initNewV8Array(j);
        this.released = false;
        addObjectReference(initNewV8Array);
    }

    public int length() {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        return v8.arrayGetSize(v8.getV8RuntimePtr(), getHandle());
    }

    public V8Array push(double d) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.addArrayDoubleItem(v8.getV8RuntimePtr(), getHandle(), d);
        return this;
    }

    public V8Array push(int i) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.addArrayIntItem(v8.getV8RuntimePtr(), getHandle(), i);
        return this;
    }

    public V8Array push(V8Value v8Value) {
        this.v8.checkThread();
        checkReleased();
        this.v8.checkRuntime(v8Value);
        if (v8Value == null) {
            V8 v8 = this.v8;
            v8.addArrayNullItem(v8.getV8RuntimePtr(), getHandle());
        } else if (v8Value.equals(V8.getUndefined())) {
            V8 v82 = this.v8;
            v82.addArrayUndefinedItem(v82.getV8RuntimePtr(), getHandle());
        } else {
            V8 v83 = this.v8;
            v83.addArrayObjectItem(v83.getV8RuntimePtr(), getHandle(), v8Value.getHandle());
        }
        return this;
    }

    public V8Array push(Object obj) {
        V8 v8;
        long v8RuntimePtr;
        long handle;
        double doubleValue;
        this.v8.checkThread();
        checkReleased();
        if (obj instanceof V8Value) {
            this.v8.checkRuntime((V8Value) obj);
        }
        if (obj == null) {
            V8 v82 = this.v8;
            v82.addArrayNullItem(v82.getV8RuntimePtr(), getHandle());
        } else if (obj.equals(V8.getUndefined())) {
            V8 v83 = this.v8;
            v83.addArrayUndefinedItem(v83.getV8RuntimePtr(), getHandle());
        } else {
            if (obj instanceof Double) {
                v8 = this.v8;
                v8RuntimePtr = v8.getV8RuntimePtr();
                handle = getHandle();
                doubleValue = ((Double) obj).doubleValue();
            } else if (obj instanceof Integer) {
                V8 v84 = this.v8;
                v84.addArrayIntItem(v84.getV8RuntimePtr(), getHandle(), ((Integer) obj).intValue());
            } else if (obj instanceof Float) {
                v8 = this.v8;
                v8RuntimePtr = v8.getV8RuntimePtr();
                handle = getHandle();
                doubleValue = ((Float) obj).doubleValue();
            } else if (obj instanceof Number) {
                v8 = this.v8;
                v8RuntimePtr = v8.getV8RuntimePtr();
                handle = getHandle();
                doubleValue = ((Number) obj).doubleValue();
            } else if (obj instanceof Boolean) {
                V8 v85 = this.v8;
                v85.addArrayBooleanItem(v85.getV8RuntimePtr(), getHandle(), ((Boolean) obj).booleanValue());
            } else if (obj instanceof String) {
                V8 v86 = this.v8;
                v86.addArrayStringItem(v86.getV8RuntimePtr(), getHandle(), (String) obj);
            } else if (!(obj instanceof V8Value)) {
                throw new IllegalArgumentException();
            } else {
                V8 v87 = this.v8;
                v87.addArrayObjectItem(v87.getV8RuntimePtr(), getHandle(), ((V8Value) obj).getHandle());
            }
            v8.addArrayDoubleItem(v8RuntimePtr, handle, doubleValue);
        }
        return this;
    }

    public V8Array push(String str) {
        this.v8.checkThread();
        checkReleased();
        if (str == null) {
            V8 v8 = this.v8;
            v8.addArrayNullItem(v8.getV8RuntimePtr(), getHandle());
        } else if (str.equals(V8.getUndefined())) {
            V8 v82 = this.v8;
            v82.addArrayUndefinedItem(v82.getV8RuntimePtr(), getHandle());
        } else {
            V8 v83 = this.v8;
            v83.addArrayStringItem(v83.getV8RuntimePtr(), getHandle(), str);
        }
        return this;
    }

    public V8Array push(boolean z) {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.addArrayBooleanItem(v8.getV8RuntimePtr(), getHandle(), z);
        return this;
    }

    public V8Array pushNull() {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.addArrayNullItem(v8.getV8RuntimePtr(), getHandle());
        return this;
    }

    public V8Array pushUndefined() {
        this.v8.checkThread();
        checkReleased();
        V8 v8 = this.v8;
        v8.addArrayUndefinedItem(v8.getV8RuntimePtr(), getHandle());
        return this;
    }

    @Override
    public String toString() {
        return (this.released || this.v8.isReleased()) ? "[Array released]" : super.toString();
    }

    @Override
    public V8Array twin() {
        return (V8Array) super.twin();
    }
}
