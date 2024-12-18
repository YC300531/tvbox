package com.alivc.conan;

import android.os.Process;
import java.io.Serializable;
import java.lang.reflect.Method;
public class b implements Serializable {
    public static final int[] a = {32, 544, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224, 32, 32, 32, 32, 8224, 32, 8224, 32};
    public static final int[] b = {288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
    private String c;
    private Method d;
    private long e;
    private long f;
    private long g;
    private long h;
    private long i;
    private long j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private long q;
    private long r;
    private long s;
    private long t;
    private int u;
    private int v;
    private int w;
    private int x;
    private final long[] y = new long[7];
    private final long[] z = new long[6];

    public b() {
        try {
            this.c = "/proc/" + Process.myPid() + "/stat";
            Method method = Process.class.getMethod("readProcFile", String.class, int[].class, String[].class, long[].class, float[].class);
            this.d = method;
            method.setAccessible(true);
        } catch (Exception unused) {
        }
    }

    public int a() {
        return this.x;
    }

    public void a(android.content.Context r28) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.alivc.conan.b.a(android.content.Context):void");
    }
}
