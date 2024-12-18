package com.alivc.conan.event;

import androidx.media3.extractor.text.ttml.TtmlNode;
import com.alivc.conan.AlivcSDKEnvironment;
import com.eclipsesource.v8.Platform;
import java.util.HashMap;
import java.util.Map;
public class a {
    private Map<String, String> a;
    private Map<String, String> b;

    public static class fun1 {
    }

    public static class a {
        private static a a = new a(null);
    }

    private a() {
    }

    public a(1 r1) {
        this();
    }

    public static a a() {
        return a.a;
    }

    private void a(Map<String, String> map, Map<String, String> map2) {
        if (map == null || map2 == null) {
            return;
        }
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                map2.put(entry.getKey(), entry.getValue());
            }
        } catch (Throwable unused) {
        }
    }

    private void e() {
        if (this.a == null) {
            this.a = new HashMap();
        }
        if (com.alivc.conan.a.a() != null) {
            this.a.put("gl_v", com.alivc.conan.a.k());
        }
        this.a.put("db", com.alivc.conan.a.l());
        this.a.put("dma", com.alivc.conan.a.m());
        this.a.put("cp", com.alivc.conan.a.i());
        this.a.put("ci", com.alivc.conan.a.h());
        this.a.put("gi", com.alivc.conan.a.j());
    }

    private void f() {
        if (this.b == null) {
            this.b = new HashMap();
        }
        this.b.put("lv", "2.0");
        if (com.alivc.conan.a.a() != null) {
            this.b.put(TtmlNode.TAG_TT, com.alivc.conan.a.c());
        }
        this.b.put("dm", com.alivc.conan.a.n());
        this.b.put("os", Platform.ANDROID);
        this.b.put("ov", com.alivc.conan.a.o());
        this.b.put("uuid", com.alivc.conan.a.b());
        if (com.alivc.conan.a.a() != null) {
            this.b.put("app_id", com.alivc.conan.a.a().getPackageName());
        }
        this.b.put("bi", "");
    }

    public void a(AlivcSDKEnvironment alivcSDKEnvironment) {
        if (this.b == null) {
            f();
        }
        Map<String, String> map = this.b;
        StringBuilder sb = new StringBuilder();
        if (alivcSDKEnvironment == null) {
            alivcSDKEnvironment = AlivcSDKEnvironment.EN_ONLINE;
        }
        sb.append(alivcSDKEnvironment.getSDKEnv());
        sb.append("");
        map.put("se", sb.toString());
    }

    public void a(String str) {
        if (this.b == null) {
            f();
        }
        this.b.put("app_n", str);
    }

    public Map<String, String> b() {
        HashMap hashMap = new HashMap();
        hashMap.put("cpu", com.alivc.conan.a.e());
        hashMap.put("mem", com.alivc.conan.a.f());
        hashMap.put("ele", com.alivc.conan.a.g());
        return hashMap;
    }

    public Map<String, String> c() {
        if (this.b == null) {
            f();
        }
        HashMap hashMap = new HashMap();
        a(this.b, hashMap);
        return hashMap;
    }

    public Map<String, String> d() {
        if (this.a == null) {
            e();
        }
        HashMap hashMap = new HashMap();
        a(this.a, hashMap);
        return hashMap;
    }
}
