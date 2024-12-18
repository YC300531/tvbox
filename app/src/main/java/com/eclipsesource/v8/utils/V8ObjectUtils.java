package com.eclipsesource.v8.utils;

import androidx.base.a.a;
import androidx.base.a.b;
import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8ArrayBuffer;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.V8TypedArray;
import com.eclipsesource.v8.V8Value;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
public class V8ObjectUtils {
    private static final Object IGNORE = new Object();
    private static final TypeAdapter DEFAULT_TYPE_ADAPTER = new DefaultTypeAdapter();

    public static class DefaultTypeAdapter implements TypeAdapter {
        @Override
        public Object adapt(int i, Object obj) {
            return TypeAdapter.DEFAULT;
        }
    }

    public static class ListWrapper {
        private List<? extends Object> list;

        public ListWrapper(List<? extends Object> list) {
            this.list = list;
        }

        public boolean equals(Object obj) {
            return (obj instanceof ListWrapper) && ((ListWrapper) obj).list == this.list;
        }

        public int hashCode() {
            return System.identityHashCode(this.list);
        }
    }

    private V8ObjectUtils() {
    }

    public static Object getTypedArray(V8Array v8Array, int i) {
        int length = v8Array.length();
        if (i == 1) {
            return v8Array.getIntegers(0, length);
        }
        if (i == 2) {
            return v8Array.getDoubles(0, length);
        }
        if (i == 3) {
            return v8Array.getBooleans(0, length);
        }
        if (i == 4) {
            return v8Array.getStrings(0, length);
        }
        throw new RuntimeException(a.h("Unsupported bulk load type: ", i));
    }

    public static Object getTypedArray(V8Array v8Array, int i, Object obj) {
        int length = v8Array.length();
        if (i == 1) {
            int[] iArr = (int[]) obj;
            if (iArr == null || iArr.length < length) {
                iArr = new int[length];
            }
            v8Array.getIntegers(0, length, iArr);
            return iArr;
        } else if (i == 2) {
            double[] dArr = (double[]) obj;
            if (dArr == null || dArr.length < length) {
                dArr = new double[length];
            }
            v8Array.getDoubles(0, length, dArr);
            return dArr;
        } else if (i == 3) {
            boolean[] zArr = (boolean[]) obj;
            if (zArr == null || zArr.length < length) {
                zArr = new boolean[length];
            }
            v8Array.getBooleans(0, length, zArr);
            return zArr;
        } else if (i == 4) {
            String[] strArr = (String[]) obj;
            if (strArr == null || strArr.length < length) {
                strArr = new String[length];
            }
            v8Array.getStrings(0, length, strArr);
            return strArr;
        } else if (i == 9) {
            byte[] bArr = (byte[]) obj;
            if (bArr == null || bArr.length < length) {
                bArr = new byte[length];
            }
            v8Array.getBytes(0, length, bArr);
            return bArr;
        } else {
            throw new RuntimeException(a.h("Unsupported bulk load type: ", i));
        }
    }

    public static Object getV8Result(V8 v8, Object obj) {
        if (obj == null) {
            return null;
        }
        Hashtable hashtable = new Hashtable();
        try {
            Object v8Result = getV8Result(v8, obj, hashtable);
            if (v8Result instanceof V8Value) {
                return ((V8Value) v8Result).twin();
            }
            for (V8Value v8Value : hashtable.values()) {
                v8Value.close();
            }
            return v8Result;
        } finally {
            for (V8Value v8Value2 : hashtable.values()) {
                v8Value2.close();
            }
        }
    }

    private static Object getV8Result(V8 v8, Object obj, Map<Object, V8Value> map) {
        return map.containsKey(obj) ? map.get(obj) : obj instanceof Map ? toV8Object(v8, (Map) obj, map) : obj instanceof List ? toV8Array(v8, (List) obj, map) : obj instanceof TypedArray ? toV8TypedArray(v8, (TypedArray) obj, map) : obj instanceof ArrayBuffer ? toV8ArrayBuffer(v8, (ArrayBuffer) obj, map) : obj;
    }

    public static Object getValue(V8Array v8Array, int i) {
        Object obj;
        V8Map v8Map = new V8Map();
        try {
            obj = v8Array.get(i);
            try {
                Object value = getValue(obj, v8Array.getType(i), v8Map, DEFAULT_TYPE_ADAPTER);
                if (value != obj || !(value instanceof V8Value)) {
                    if (obj instanceof Releasable) {
                        ((Releasable) obj).release();
                    }
                    v8Map.close();
                    return value;
                }
                V8Value twin = ((V8Value) value).twin();
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                return twin;
            } catch (Throwable th) {
                th = th;
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            obj = null;
        }
    }

    public static Object getValue(V8Array v8Array, int i, TypeAdapter typeAdapter) {
        Object obj;
        V8Map v8Map = new V8Map();
        try {
            obj = v8Array.get(i);
            try {
                Object value = getValue(obj, v8Array.getType(i), v8Map, typeAdapter);
                if (value != obj || !(value instanceof V8Value)) {
                    if (obj instanceof Releasable) {
                        ((Releasable) obj).release();
                    }
                    v8Map.close();
                    return value;
                }
                V8Value twin = ((V8Value) value).twin();
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                return twin;
            } catch (Throwable th) {
                th = th;
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            obj = null;
        }
    }

    public static Object getValue(V8Object v8Object, String str) {
        return getValue(v8Object, str, DEFAULT_TYPE_ADAPTER);
    }

    public static Object getValue(V8Object v8Object, String str, TypeAdapter typeAdapter) {
        Object obj;
        V8Map v8Map = new V8Map();
        try {
            obj = v8Object.get(str);
            try {
                Object value = getValue(obj, v8Object.getType(str), v8Map, typeAdapter);
                if (value != obj || !(value instanceof V8Value)) {
                    if (obj instanceof Releasable) {
                        ((Releasable) obj).release();
                    }
                    v8Map.close();
                    return value;
                }
                V8Value twin = ((V8Value) value).twin();
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                return twin;
            } catch (Throwable th) {
                th = th;
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
                v8Map.close();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            obj = null;
        }
    }

    public static Object getValue(Object obj) {
        return getValue(obj, DEFAULT_TYPE_ADAPTER);
    }

    private static Object getValue(Object obj, int i, V8Map<Object> v8Map, TypeAdapter typeAdapter) {
        Object adapt = typeAdapter.adapt(i, obj);
        if (TypeAdapter.DEFAULT != adapt) {
            return adapt;
        }
        if (i != 10) {
            if (i != 99) {
                switch (i) {
                    case 0:
                        return null;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        return obj;
                    case 5:
                        return toList((V8Array) obj, v8Map, typeAdapter);
                    case 6:
                        return toMap((V8Object) obj, v8Map, typeAdapter);
                    case 7:
                        return IGNORE;
                    case 8:
                        return new TypedArray((V8TypedArray) obj);
                    default:
                        StringBuilder d = b.d("Cannot convert type ");
                        d.append(V8Value.getStringRepresentation(i));
                        throw new IllegalStateException(d.toString());
                }
            }
            return V8.getUndefined();
        }
        return new ArrayBuffer((V8ArrayBuffer) obj);
    }

    public static Object getValue(Object obj, TypeAdapter typeAdapter) {
        V8Map v8Map = new V8Map();
        try {
            return obj instanceof V8Value ? getValue(obj, ((V8Value) obj).getV8Type(), v8Map, typeAdapter) : obj;
        } finally {
            v8Map.close();
        }
    }

    public static void pushValue(V8 v8, V8Array v8Array, Object obj) {
        Hashtable hashtable = new Hashtable();
        try {
            pushValue(v8, v8Array, obj, hashtable);
        } finally {
            for (V8Value v8Value : hashtable.values()) {
                v8Value.close();
            }
        }
    }

    private static void pushValue(V8 v8, V8Array v8Array, Object obj, Map<Object, V8Value> map) {
        V8Value v8Array2;
        if (obj == null) {
            v8Array.pushUndefined();
            return;
        }
        if (!(obj instanceof Integer)) {
            if (obj instanceof Long) {
                v8Array.push(new Double(((Long) obj).longValue()));
                return;
            } else if (!(obj instanceof Double) && !(obj instanceof Float)) {
                if (obj instanceof String) {
                    v8Array.push((String) obj);
                    return;
                } else if (!(obj instanceof Boolean)) {
                    if (obj instanceof TypedArray) {
                        v8Array2 = toV8TypedArray(v8, (TypedArray) obj, map);
                    } else if (obj instanceof ArrayBuffer) {
                        v8Array2 = toV8ArrayBuffer(v8, (ArrayBuffer) obj, map);
                    } else if (obj instanceof V8Value) {
                        v8Array.push((V8Value) obj);
                        return;
                    } else if (obj instanceof Map) {
                        v8Array2 = toV8Object(v8, (Map) obj, map);
                    } else if (!(obj instanceof List)) {
                        StringBuilder d = b.d("Unsupported Object of type: ");
                        d.append(obj.getClass());
                        throw new IllegalStateException(d.toString());
                    } else {
                        v8Array2 = toV8Array(v8, (List) obj, map);
                    }
                    v8Array.push(v8Array2);
                    return;
                }
            }
        }
        v8Array.push(obj);
    }

    private static void setValue(V8 v8, V8Object v8Object, String str, Object obj, Map<Object, V8Value> map) {
        V8Value v8Array;
        double floatValue;
        if (obj == null) {
            v8Object.addUndefined(str);
        } else if (obj instanceof Integer) {
            v8Object.add(str, ((Integer) obj).intValue());
        } else {
            if (obj instanceof Long) {
                floatValue = ((Long) obj).longValue();
            } else if (obj instanceof Double) {
                floatValue = ((Double) obj).doubleValue();
            } else if (!(obj instanceof Float)) {
                if (obj instanceof String) {
                    v8Object.add(str, (String) obj);
                    return;
                } else if (obj instanceof Boolean) {
                    v8Object.add(str, ((Boolean) obj).booleanValue());
                    return;
                } else {
                    if (obj instanceof TypedArray) {
                        v8Array = toV8TypedArray(v8, (TypedArray) obj, map);
                    } else if (obj instanceof ArrayBuffer) {
                        v8Array = toV8ArrayBuffer(v8, (ArrayBuffer) obj, map);
                    } else if (obj instanceof V8Value) {
                        v8Object.add(str, (V8Value) obj);
                        return;
                    } else if (obj instanceof Map) {
                        v8Array = toV8Object(v8, (Map) obj, map);
                    } else if (!(obj instanceof List)) {
                        StringBuilder d = b.d("Unsupported Object of type: ");
                        d.append(obj.getClass());
                        throw new IllegalStateException(d.toString());
                    } else {
                        v8Array = toV8Array(v8, (List) obj, map);
                    }
                    v8Object.add(str, v8Array);
                    return;
                }
            } else {
                floatValue = ((Float) obj).floatValue();
            }
            v8Object.add(str, floatValue);
        }
    }

    public static List<? super Object> toList(V8Array v8Array) {
        return toList(v8Array, DEFAULT_TYPE_ADAPTER);
    }

    public static List<? super Object> toList(V8Array v8Array, TypeAdapter typeAdapter) {
        V8Map v8Map = new V8Map();
        try {
            return toList(v8Array, v8Map, typeAdapter);
        } finally {
            v8Map.close();
        }
    }

    private static List<? super Object> toList(V8Array v8Array, V8Map<Object> v8Map, TypeAdapter typeAdapter) {
        if (v8Array == null) {
            return Collections.emptyList();
        }
        if (v8Map.containsKey(v8Array)) {
            return (List) v8Map.get(v8Array);
        }
        ArrayList arrayList = new ArrayList();
        v8Map.put((V8Value) v8Array, (V8Array) arrayList);
        for (int i = 0; i < v8Array.length(); i++) {
            Object obj = null;
            try {
                obj = v8Array.get(i);
                Object value = getValue(obj, v8Array.getType(i), v8Map, typeAdapter);
                if (value != IGNORE) {
                    arrayList.add(value);
                }
            } finally {
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
            }
        }
        return arrayList;
    }

    public static Map<String, ? super Object> toMap(V8Object v8Object) {
        return toMap(v8Object, DEFAULT_TYPE_ADAPTER);
    }

    public static Map<String, ? super Object> toMap(V8Object v8Object, TypeAdapter typeAdapter) {
        V8Map v8Map = new V8Map();
        try {
            return toMap(v8Object, v8Map, typeAdapter);
        } finally {
            v8Map.close();
        }
    }

    private static Map<String, ? super Object> toMap(V8Object v8Object, V8Map<Object> v8Map, TypeAdapter typeAdapter) {
        String[] keys;
        if (v8Object == null) {
            return Collections.emptyMap();
        }
        if (v8Map.containsKey(v8Object)) {
            return (Map) v8Map.get(v8Object);
        }
        V8PropertyMap v8PropertyMap = new V8PropertyMap();
        v8Map.put((V8Value) v8Object, (V8Object) v8PropertyMap);
        for (String str : v8Object.getKeys()) {
            Object obj = null;
            try {
                obj = v8Object.get(str);
                Object value = getValue(obj, v8Object.getType(str), v8Map, typeAdapter);
                if (value != IGNORE) {
                    v8PropertyMap.put((V8PropertyMap) str, (String) value);
                }
            } finally {
                if (obj instanceof Releasable) {
                    ((Releasable) obj).release();
                }
            }
        }
        return v8PropertyMap;
    }

    public static V8Array toV8Array(V8 v8, List<? extends Object> list) {
        Hashtable hashtable = new Hashtable();
        try {
            return toV8Array(v8, list, hashtable).twin();
        } finally {
            for (V8Value v8Value : hashtable.values()) {
                v8Value.close();
            }
        }
    }

    private static V8Array toV8Array(V8 v8, List<? extends Object> list, Map<Object, V8Value> map) {
        if (map.containsKey(new ListWrapper(list))) {
            return (V8Array) map.get(new ListWrapper(list));
        }
        V8Array v8Array = new V8Array(v8);
        map.put(new ListWrapper(list), v8Array);
        for (int i = 0; i < list.size(); i++) {
            try {
                pushValue(v8, v8Array, list.get(i), map);
            } catch (IllegalStateException e) {
                v8Array.close();
                throw e;
            }
        }
        return v8Array;
    }

    private static V8ArrayBuffer toV8ArrayBuffer(V8 v8, ArrayBuffer arrayBuffer, Map<Object, V8Value> map) {
        if (map.containsKey(arrayBuffer)) {
            return (V8ArrayBuffer) map.get(arrayBuffer);
        }
        V8ArrayBuffer v8ArrayBuffer = arrayBuffer.getV8ArrayBuffer();
        map.put(arrayBuffer, v8ArrayBuffer);
        return v8ArrayBuffer;
    }

    public static V8Object toV8Object(V8 v8, Map<String, ? extends Object> map) {
        Hashtable hashtable = new Hashtable();
        try {
            return toV8Object(v8, map, hashtable).twin();
        } finally {
            for (V8Value v8Value : hashtable.values()) {
                v8Value.close();
            }
        }
    }

    private static V8Object toV8Object(V8 v8, Map<String, ? extends Object> map, Map<Object, V8Value> map2) {
        if (map2.containsKey(map)) {
            return (V8Object) map2.get(map);
        }
        V8Object v8Object = new V8Object(v8);
        map2.put(map, v8Object);
        try {
            for (Map.Entry<String, ? extends Object> entry : map.entrySet()) {
                setValue(v8, v8Object, entry.getKey(), entry.getValue(), map2);
            }
            return v8Object;
        } catch (IllegalStateException e) {
            v8Object.close();
            throw e;
        }
    }

    private static V8TypedArray toV8TypedArray(V8 v8, TypedArray typedArray, Map<Object, V8Value> map) {
        if (map.containsKey(typedArray)) {
            return (V8TypedArray) map.get(typedArray);
        }
        V8TypedArray v8TypedArray = typedArray.getV8TypedArray();
        map.put(typedArray, v8TypedArray);
        return v8TypedArray;
    }
}
