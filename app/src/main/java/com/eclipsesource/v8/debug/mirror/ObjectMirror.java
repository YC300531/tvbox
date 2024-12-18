package com.eclipsesource.v8.debug.mirror;

import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
public class ObjectMirror extends ValueMirror {
    private static final String PROPERTIES = "properties";
    private static final String PROPERTY_NAMES = "propertyNames";

    public enum PropertyKind {
        Named(1),
        Indexed(2);
        
        public int index;

        PropertyKind(int i) {
            this.index = i;
        }
    }

    public ObjectMirror(V8Object v8Object) {
        super(v8Object);
    }

    public PropertiesArray getProperties(PropertyKind propertyKind, int i) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(propertyKind.index);
        v8Array.push(i);
        V8Array v8Array2 = null;
        try {
            v8Array2 = this.v8Object.executeArrayFunction(PROPERTIES, v8Array);
            return new PropertiesArray(v8Array2);
        } finally {
            v8Array.close();
            if (v8Array2 != null && !v8Array2.isReleased()) {
                v8Array2.close();
            }
        }
    }

    public String[] getPropertyNames(PropertyKind propertyKind, int i) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(propertyKind.index);
        v8Array.push(i);
        V8Array v8Array2 = null;
        try {
            v8Array2 = this.v8Object.executeArrayFunction(PROPERTY_NAMES, v8Array);
            int length = v8Array2.length();
            String[] strArr = new String[length];
            for (int i2 = 0; i2 < length; i2++) {
                strArr[i2] = v8Array2.getString(i2);
            }
            v8Array.close();
            v8Array2.close();
            return strArr;
        } catch (Throwable th) {
            v8Array.close();
            if (v8Array2 != null) {
                v8Array2.close();
            }
            throw th;
        }
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public String toString() {
        return this.v8Object.toString();
    }
}
