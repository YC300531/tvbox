package com.github.catvod.crawler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class JarLoader {
    private ConcurrentHashMap<String, Method> proxyMethods = new ConcurrentHashMap<>();
    private volatile String recentJarKey = "";

    public Object[] proxyInvoke(Map map) {
        try {
            Method method = this.proxyMethods.get(this.recentJarKey);
            if (method != null) {
                return (Object[]) method.invoke(null, map);
            }
        } catch (Throwable unused) {
        }
        return null;
    }
}
