package com.alivc.conan;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import androidx.appcompat.widget.ActivityChooserModel;
import androidx.media3.common.C;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.aliyun.utils.DeviceInfoUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
public class a {
    public static final File a = new File(Environment.getExternalStorageDirectory(), DeviceInfoUtils.DATA_DIRECTORY);
    private static String b;
    private static String c;
    private static String d;
    private static Context e;
    private static int f;
    private static b g;

    public static Context a() {
        return e;
    }

    public static synchronized java.lang.String b() {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.alivc.conan.a.b():java.lang.String");
    }

    public static void b(final File file, final String str) {
        if (file == null || TextUtils.isEmpty(str)) {
            return;
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.alivc.conan.a.1.run():void");
            }
        }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
    }

    public static String c() {
        try {
            Context context = e;
            return (context == null || context.getResources() == null || e.getResources().getConfiguration() == null) ? "phone" : (e.getResources().getConfiguration().screenLayout & 15) >= 3 ? "pad" : "phone";
        } catch (Throwable unused) {
            return "phone";
        }
    }

    public static String d() {
        String replace = UUID.randomUUID().toString().replace("-", "");
        c = replace;
        return replace;
    }

    public static String e() {
        if (g == null) {
            g = new b();
        }
        g.a(a());
        return String.valueOf(g.a());
    }

    public static String f() {
        Context context = e;
        if (context != null) {
            try {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityChooserModel.ATTRIBUTE_ACTIVITY);
                if (activityManager != null) {
                    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                    activityManager.getMemoryInfo(memoryInfo);
                    long j = memoryInfo.totalMem;
                    return j > 0 ? String.valueOf((int) ((1.0f - ((((float) memoryInfo.availMem) * 1.0f) / ((float) j))) * 100.0f)) : SessionDescription.SUPPORTED_SDP_VERSION;
                }
                return SessionDescription.SUPPORTED_SDP_VERSION;
            } catch (Throwable unused) {
                return SessionDescription.SUPPORTED_SDP_VERSION;
            }
        }
        return SessionDescription.SUPPORTED_SDP_VERSION;
    }

    public static String g() {
        if (e != null) {
            try {
                Intent registerReceiver = e.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                return String.valueOf((int) (((registerReceiver != null ? registerReceiver.getIntExtra("level", -1) : -1) / (registerReceiver != null ? registerReceiver.getIntExtra("scale", -1) : -1)) * 100.0f));
            } catch (Throwable unused) {
                return SessionDescription.SUPPORTED_SDP_VERSION;
            }
        }
        return SessionDescription.SUPPORTED_SDP_VERSION;
    }

    public static String h() {
        return Build.HARDWARE;
    }

    public static String i() {
        if (TextUtils.isEmpty(d)) {
            u();
            return d;
        }
        return d;
    }

    public static String j() {
        return "";
    }

    public static String k() {
        ConfigurationInfo deviceConfigurationInfo;
        Context context = e;
        if (context != null) {
            try {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityChooserModel.ATTRIBUTE_ACTIVITY);
                if (activityManager == null || (deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo()) == null) {
                    return null;
                }
                return Integer.toHexString(Integer.parseInt(deviceConfigurationInfo.reqGlEsVersion + ""));
            } catch (Throwable unused) {
                return null;
            }
        }
        return null;
    }

    public static String l() {
        return Build.BRAND;
    }

    public static String m() {
        return Build.MANUFACTURER;
    }

    public static String n() {
        return Build.MODEL;
    }

    public static String o() {
        return Build.VERSION.RELEASE;
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String p() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
    }

    public static boolean q() {
        Context context = e;
        return context != null && context.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", Process.myPid(), Process.myUid()) == 0;
    }

    public static boolean r() {
        Context context = e;
        return context != null && context.checkPermission("android.permission.ACCESS_NETWORK_STATE", Process.myPid(), Process.myUid()) == 0;
    }

    public static int s() {
        int i = f;
        f = i + 1;
        return i;
    }

    @DoNotProguard
    public static void setSDKContext(Context context) {
        e = context != null ? context.getApplicationContext() : context;
        if (g == null) {
            g = new b();
        }
        g.a(context);
    }

    private static void u() {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.alivc.conan.a.u():void");
    }
}
