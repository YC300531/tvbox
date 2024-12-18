package com.sun.jna;

import androidx.base.r5.b;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.WeakHashMap;
public class NativeMappedConverter implements TypeConverter {
    private static final Map<Class<?>, Reference<NativeMappedConverter>> converters = new WeakHashMap();
    private final NativeMapped instance;
    private final Class<?> nativeType;
    private final Class<?> type;

    public NativeMappedConverter(Class<?> cls) {
        if (!NativeMapped.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException(b.f("Type must derive from ", NativeMapped.class));
        }
        this.type = cls;
        NativeMapped defaultValue = defaultValue();
        this.instance = defaultValue;
        this.nativeType = defaultValue.nativeType();
    }

    public static NativeMappedConverter getInstance(Class<?> cls) {
        NativeMappedConverter nativeMappedConverter;
        Map<Class<?>, Reference<NativeMappedConverter>> map = converters;
        synchronized (map) {
            Reference<NativeMappedConverter> reference = map.get(cls);
            nativeMappedConverter = reference != null ? reference.get() : null;
            if (nativeMappedConverter == null) {
                nativeMappedConverter = new NativeMappedConverter(cls);
                map.put(cls, new SoftReference(nativeMappedConverter));
            }
        }
        return nativeMappedConverter;
    }

    public NativeMapped defaultValue() {
        return (NativeMapped) (this.type.isEnum() ? this.type.getEnumConstants()[0] : Klass.newInstance(this.type));
    }

    @Override
    public Object fromNative(Object obj, FromNativeContext fromNativeContext) {
        return this.instance.fromNative(obj, fromNativeContext);
    }

    @Override
    public Class<?> nativeType() {
        return this.nativeType;
    }

    @Override
    public Object toNative(Object obj, ToNativeContext toNativeContext) {
        if (obj == null) {
            if (Pointer.class.isAssignableFrom(this.nativeType)) {
                return null;
            }
            obj = defaultValue();
        }
        return ((NativeMapped) obj).toNative();
    }
}
