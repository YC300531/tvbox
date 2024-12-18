package com.eclipsesource.v8.utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
class V8PropertyMap<V> implements Map<String, V> {
    private Hashtable<String, V> map = new Hashtable<>();
    private Set<String> nulls = new HashSet();

    @Override
    public void clear() {
        this.map.clear();
        this.nulls.clear();
    }

    @Override
    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj) || this.nulls.contains(obj);
    }

    @Override
    public boolean containsValue(Object obj) {
        if (obj != null || this.nulls.isEmpty()) {
            if (obj == null) {
                return false;
            }
            return this.map.containsValue(obj);
        }
        return true;
    }

    @Override
    public Set<Map.Entry<String, V>> entrySet() {
        HashSet hashSet = new HashSet(this.map.entrySet());
        for (String str : this.nulls) {
            hashSet.add(new AbstractMap.SimpleEntry(str, null));
        }
        return hashSet;
    }

    @Override
    public V get(Object obj) {
        if (this.nulls.contains(obj)) {
            return null;
        }
        return this.map.get(obj);
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty() && this.nulls.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        HashSet hashSet = new HashSet(this.map.keySet());
        hashSet.addAll(this.nulls);
        return hashSet;
    }

    @Override
    public Object put(String str, Object obj) {
        return put(str, (String) obj);
    }

    public V put(String str, V v) {
        if (v != null) {
            if (this.nulls.contains(str)) {
                this.nulls.remove(str);
            }
            return this.map.put(str, v);
        }
        if (this.map.containsKey(str)) {
            this.map.remove(str);
        }
        this.nulls.add(str);
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> map) {
        for (Map.Entry<? extends String, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), (String) entry.getValue());
        }
    }

    @Override
    public V remove(Object obj) {
        if (this.nulls.contains(obj)) {
            this.nulls.remove(obj);
            return null;
        }
        return this.map.remove(obj);
    }

    @Override
    public int size() {
        return this.nulls.size() + this.map.size();
    }

    @Override
    public Collection<V> values() {
        ArrayList arrayList = new ArrayList(this.map.values());
        for (int i = 0; i < this.nulls.size(); i++) {
            arrayList.add(null);
        }
        return arrayList;
    }
}
