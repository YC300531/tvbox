package com.alivc.conan.crash;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Process;
import android.text.TextUtils;
import androidx.base.a.b;
import com.alivc.conan.AlivcConan;
import com.alivc.conan.DoNotProguard;
import com.alivc.conan.a;
import com.alivc.conan.event.AlivcEventReporter;
import com.alivc.conan.log.AlivcLog;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@DoNotProguard
public final class AlivcCrashReport extends AlivcConan {
    private AlivcEventReporter a;
    private AlivcLog b;
    private AlivcCrashConfig c;
    private List<String> d;
    private String e;
    private List<String> f;

    @DoNotProguard
    public AlivcCrashReport(AlivcCrashConfig alivcCrashConfig) {
        try {
            this.c = alivcCrashConfig;
            this.d = Collections.synchronizedList(new ArrayList());
            this.f = Collections.synchronizedList(new ArrayList());
            if (!TextUtils.isEmpty(alivcCrashConfig.getCrashFilter())) {
                this.d.add(alivcCrashConfig.getCrashFilter());
            }
            this.b = AlivcLog.getLogInstanceById(alivcCrashConfig.getAlivcLogId());
            this.a = AlivcEventReporter.getEventReporterById(alivcCrashConfig.getEventReportId());
            this.e = new File(a.a, "crash").getAbsolutePath();
            a();
        } catch (Throwable unused) {
        }
    }

    private String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = a.a;
        if (file.exists() || file.mkdir()) {
            File file2 = new File(file, "crash");
            if (file2.exists() || file2.mkdir()) {
                File file3 = new File(file2, String.format("Crash_%s_Android.txt", str));
                file3.deleteOnExit();
                try {
                    file3.createNewFile();
                } catch (IOException unused) {
                }
                return file3.getAbsolutePath();
            }
            return null;
        }
        return null;
    }

    private String a(String str, long j, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(b());
        sb.append("\n");
        sb.append("Application Specific Information:\n");
        sb.append("*** Terminating app due to uncaught exception, reason:\n");
        sb.append(str);
        sb.append("\n");
        sb.append("Last Exception Backtrace:\n");
        sb.append(str3);
        sb.append("\n");
        sb.append("Binary Images:\n");
        sb.append("ThreadID: ");
        sb.append(j);
        sb.append("\n");
        sb.append("ThreadName: ");
        sb.append(str2);
        sb.append("\n");
        sb.append("CPU Info: ");
        sb.append(a.h());
        sb.append("\n");
        sb.append("CPU Processor: ");
        sb.append(a.i());
        sb.append("\n");
        sb.append("GPU Info: ");
        sb.append(a.j());
        sb.append("\n");
        for (String str4 : this.f) {
            if (!TextUtils.isEmpty(str4)) {
                sb.append(str4);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @SuppressLint({"StaticFieldLeak"})
    private void a() {
        new AsyncTask<Void, Integer, Void>() {
            @Override
            public Void doInBackground(Void... voidArr) {
                if (b != null && !TextUtils.isEmpty(e)) {
                    b.uploadFile(e, null);
                }
                return null;
            }
        }.execute(new Void[0]);
    }

    private String b() {
        StringBuilder d = b.d("TraceId: ");
        d.append(this.c.getCrashTraceId());
        d.append("\n");
        d.append("Process: ");
        d.append(Process.myPid());
        d.append("\n");
        d.append("Path: ");
        d.append(this.e);
        d.append("\n");
        d.append("SDK Version: ");
        d.append("2.0");
        d.append("\n");
        d.append("Harware Model: ");
        d.append(a.n());
        d.append("\n");
        d.append("OS Version: ");
        d.append(a.o());
        d.append("\n");
        d.append("Date/Time: ");
        d.append(a.p());
        d.append("\n");
        return d.toString();
    }

    @DoNotProguard
    public void addCrashFilter(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.d.add(str);
    }

    @DoNotProguard
    public void addCrashInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f.add(str);
    }

    @DoNotProguard
    public void removeCrashFilter(String str) {
        if (TextUtils.isEmpty(str) || !this.d.contains(str)) {
            return;
        }
        this.d.remove(str);
    }

    @com.alivc.conan.DoNotProguard
    public void reportCrashWithException(java.lang.String r7, java.lang.String r8, long r9, java.lang.String r11, java.lang.String r12) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.alivc.conan.crash.AlivcCrashReport.reportCrashWithException(java.lang.String, java.lang.String, long, java.lang.String, java.lang.String):void");
    }

    @DoNotProguard
    public void updateTraceId(String str) {
        AlivcCrashConfig alivcCrashConfig;
        if (TextUtils.isEmpty(str) || (alivcCrashConfig = this.c) == null) {
            return;
        }
        alivcCrashConfig.setCrashTraceId(str);
    }
}
