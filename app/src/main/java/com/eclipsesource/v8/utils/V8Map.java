package com.eclipsesource.v8.utils;

import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8Value;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
public class V8Map<V> implements Map<V8Value, V>, Releasable {
    private Map<V8Value, V> map = new HashMap();
    private Map<V8Value, V8Value> twinMap = new HashMap();

    @Override
    public void clear() {
        this.map.clear();
        for (V8Value v8Value : this.twinMap.keySet()) {
            v8Value.close();
        }
        this.twinMap.clear();
    }

    @Override
    public void close() {
        clear();
    }

    @Override
    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override
    public boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    @Override
    public Set<Map.Entry<V8Value, V>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public V get(Object obj) {
        return this.map.get(obj);
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Set<V8Value> keySet() {
        return this.map.keySet();
    }

    public V put(V8Value v8Value, V v) {
        remove(v8Value);
        V8Value twin = v8Value.twin();
        this.twinMap.put(twin, twin);
        return this.map.put(twin, v);
    }

    @Override
    public Object put(V8Value v8Value, Object obj) {
        return put(v8Value, (V8Value) obj);
    }

    @Override
    public void putAll(Map<? extends V8Value, ? extends V> map) {
        for (Map.Entry<? extends V8Value, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), (V8Value) entry.getValue());
        }
    }

    @Override
    @Deprecated
    public void release() {
        close();
    }

    @Override
    public V remove(Object obj) {
        V remove = this.map.remove(obj);
        V8Value remove2 = this.twinMap.remove(obj);
        if (remove2 != null) {
            remove2.close();
        }
        return remove;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }
}
