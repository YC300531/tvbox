package com.alivc.conan.crash;

import android.text.TextUtils;
import android.util.Log;
import com.alivc.conan.AlivcConan;
import com.alivc.conan.DoNotProguard;
import java.lang.Thread;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@DoNotProguard
public class AlivcCrashHandler extends AlivcConan {
    private Thread.UncaughtExceptionHandler a;
    private Thread.UncaughtExceptionHandler b;
    private Set<Throwable> c;
    private Map<String, AlivcCrashReport> d;
    private int e;

    public static class a {
        private static AlivcCrashHandler a = new AlivcCrashHandler();
    }

    private AlivcCrashHandler() {
        this.e = 100;
        this.c = new HashSet();
        this.d = Collections.synchronizedMap(new HashMap());
    }

    private void a() {
        if (this.a == null) {
            this.a = new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable th) {
                    if (th == null) {
                        return;
                    }
                    for (Throwable th2 : c) {
                        if (th2 == th) {
                            return;
                        }
                    }
                    c.add(th);
                    try {
                        String message = th.getMessage();
                        String stackTraceString = Log.getStackTraceString(th);
                        long id = thread != null ? thread.getId() : -1L;
                        for (Map.Entry entry : d.entrySet()) {
                            ((AlivcCrashReport) entry.getValue()).reportCrashWithException(message, stackTraceString, id, thread.getName(), com.alivc.conan.a.p());
                        }
                    } catch (Throwable unused) {
                    }
                    if (b != a) {
                        b.uncaughtException(thread, th);
                    }
                }
            };
        }
    }

    private void b() {
        if (Thread.getDefaultUncaughtExceptionHandler() != this.a) {
            this.b = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this.a);
        }
    }

    @DoNotProguard
    public static AlivcCrashHandler getInstance() {
        return a.a;
    }

    @DoNotProguard
    public void addCrashFilter(String str, String str2) {
        AlivcCrashReport alivcCrashReport;
        if (TextUtils.isEmpty(str2) || (alivcCrashReport = this.d.get(str)) == null) {
            return;
        }
        alivcCrashReport.addCrashFilter(str2);
    }

    @DoNotProguard
    public void addCrashInfo(String str, String str2) {
        AlivcCrashReport alivcCrashReport;
        if (TextUtils.isEmpty(str2) || (alivcCrashReport = this.d.get(str)) == null) {
            return;
        }
        alivcCrashReport.addCrashInfo(str2);
    }

    @DoNotProguard
    public String createWithCrashConfig(AlivcCrashConfig alivcCrashConfig) {
        if (alivcCrashConfig != null) {
            int i = this.e + 1;
            this.e = i;
            String valueOf = String.valueOf(i);
            this.d.put(valueOf, new AlivcCrashReport(alivcCrashConfig));
            return valueOf;
        }
        throw new IllegalArgumentException("Unable to initialize the CrashHandler with illegal configuration!");
    }

    @DoNotProguard
    public void registCrashHandler() {
        a();
        b();
    }

    @DoNotProguard
    public void releaseWithCrashId(String str) {
        this.d.remove(str);
    }

    @DoNotProguard
    public void removeCrashFilter(String str, String str2) {
        AlivcCrashReport alivcCrashReport;
        if (TextUtils.isEmpty(str2) || (alivcCrashReport = this.d.get(str)) == null) {
            return;
        }
        alivcCrashReport.removeCrashFilter(str2);
    }

    @DoNotProguard
    public void unRegistCrashHandler() {
        if (this.d.isEmpty()) {
            Thread.setDefaultUncaughtExceptionHandler(this.b);
            this.a = null;
            this.d.clear();
            this.c.clear();
        }
    }

    @DoNotProguard
    public void updateTraceId(String str, String str2) {
        AlivcCrashReport alivcCrashReport;
        if (TextUtils.isEmpty(str2) || (alivcCrashReport = this.d.get(str)) == null) {
            return;
        }
        alivcCrashReport.updateTraceId(str2);
    }
}
