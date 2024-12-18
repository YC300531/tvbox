package com.alivc.conan.log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.alivc.conan.AlivcConan;
import com.alivc.conan.DoNotProguard;
import com.alivc.conan.a;
import com.aliyun.utils.DeviceInfoUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
@DoNotProguard
public class AlivcLog extends AlivcConan {
    private static String a = new File(a.a, "log").getAbsolutePath();
    private static final Map<Long, AlivcLog> b = new HashMap();
    private Context c;
    private long d;
    private boolean e;
    private AlivcLogListener f;
    @DoNotProguard
    private String mAccessKey;
    @DoNotProguard
    private String mBucket;
    @DoNotProguard
    private int mBusinessType;
    @DoNotProguard
    private String mClientID;
    @DoNotProguard
    private String mDeviceModel;
    @DoNotProguard
    private String mEndPoint;
    @DoNotProguard
    private String mExpireTime;
    @DoNotProguard
    private String mSecretKey;
    @DoNotProguard
    private String mSecurityToken;
    @DoNotProguard
    private String mTerminalType;
    @DoNotProguard
    private boolean mUseExternalAuth;
    @DoNotProguard
    private long mNativeHandler = 0;
    @DoNotProguard
    private long mNativeCallback = 0;
    private BroadcastReceiver g = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            a(context);
        }
    };

    @DoNotProguard
    public AlivcLog(AlivcLogConfig alivcLogConfig) {
        Context a2 = a.a();
        this.c = a2;
        if (a2 == null) {
            throw new IllegalArgumentException("Context must NOT null, check setSDKContext");
        }
        if (alivcLogConfig == null) {
            throw new IllegalArgumentException("Unable to initialize the AlivcLog with empty configuration!");
        }
        try {
            this.e = true;
            this.mClientID = a.b();
            this.mBusinessType = alivcLogConfig.getBusinessType().getBizType();
            this.mTerminalType = a.c();
            this.mDeviceModel = a.n();
            this.mUseExternalAuth = alivcLogConfig.isUseExternalAuth();
            this.mEndPoint = alivcLogConfig.getEndPoint();
            this.mBucket = alivcLogConfig.getBucket();
            this.mAccessKey = alivcLogConfig.getAccessKey();
            this.mSecretKey = alivcLogConfig.getSecretKey();
            this.mSecurityToken = alivcLogConfig.getSecurityToken();
            this.mExpireTime = alivcLogConfig.getExpireTime();
            if (!a.q()) {
                a = new File(this.c.getExternalCacheDir(), DeviceInfoUtils.DATA_DIRECTORY).getAbsolutePath();
            }
            initNative();
            if (this.mNativeHandler != 0) {
                long logId = getLogId();
                if (logId != -1) {
                    this.d = logId;
                    b.put(Long.valueOf(logId), this);
                }
                setLogFileCachePathNative(a);
                a(this.c);
                a();
            }
        } catch (Throwable unused) {
        }
    }

    private void a() {
        if (this.c != null) {
            this.c.registerReceiver(this.g, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    public void a(Context context) {
        NetworkInfo networkInfo;
        NetworkInfo networkInfo2;
        if (context != null) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                int i = 1;
                boolean z = a.r() && (networkInfo2 = connectivityManager.getNetworkInfo(1)) != null && networkInfo2.isConnected();
                boolean z2 = a.r() && (networkInfo = connectivityManager.getNetworkInfo(0)) != null && networkInfo.isConnected();
                if (this.mNativeHandler != 0) {
                    if (z) {
                        i = 2;
                    } else if (!z2) {
                        i = 0;
                    }
                    setNetworkTypeNative(i);
                }
            } catch (Throwable unused) {
            }
        }
    }

    private void b() {
        Context context = this.c;
        if (context != null) {
            context.unregisterReceiver(this.g);
        }
    }

    @DoNotProguard
    private native void destoryNative();

    @DoNotProguard
    private native void disableLogNative();

    @DoNotProguard
    private native long getLogIdNative();

    @DoNotProguard
    public static AlivcLog getLogInstanceById(long j) {
        return b.get(Long.valueOf(j));
    }

    @DoNotProguard
    private native void initNative();

    @DoNotProguard
    private native int logMessageNative(int i, String str);

    @DoNotProguard
    private native int logMessageTagNative(int i, String str, String str2);

    @DoNotProguard
    private void onCreateLogFileSuccess(String str) {
        AlivcLogListener alivcLogListener = this.f;
        if (alivcLogListener != null) {
            alivcLogListener.onAlivcLogCreateLogFileSuccess(this, str);
        }
    }

    @DoNotProguard
    private void onInitComplete() {
        AlivcLogListener alivcLogListener = this.f;
        if (alivcLogListener != null) {
            alivcLogListener.onAlivcLogInitComplete(this);
        }
    }

    @DoNotProguard
    private void onLogErrorOccur(int i) {
        AlivcLogListener alivcLogListener = this.f;
        if (alivcLogListener != null) {
            alivcLogListener.onLogErrorOccur(this, i);
        }
    }

    @DoNotProguard
    private void onStsExpired() {
        AlivcLogListener alivcLogListener = this.f;
        if (alivcLogListener != null) {
            alivcLogListener.onStsExpired(this);
        }
    }

    @DoNotProguard
    private void onStsWillExpireSoon(long j) {
        AlivcLogListener alivcLogListener = this.f;
        if (alivcLogListener != null) {
            alivcLogListener.onStsWillExpireSoon(this, j);
        }
    }

    @DoNotProguard
    private void onUploadFileSuccess(String str, String str2) {
        AlivcLogListener alivcLogListener = this.f;
        if (alivcLogListener != null) {
            alivcLogListener.onAlivcLogUploadLogFileSuccess(this, str, str2);
        }
    }

    @DoNotProguard
    private native void removeLogFileAfterUploadNative(boolean z);

    @DoNotProguard
    private native int resetAcessTokenInfoNative();

    @DoNotProguard
    private native int saveCrashInfoToLogFileNative(String str, String str2);

    @DoNotProguard
    private native void setLogFileCachePathNative(String str);

    @DoNotProguard
    private native void setLogLevelNative(int i);

    @DoNotProguard
    private native void setLogModeNative(int i);

    @DoNotProguard
    private native void setMaxFileSizeNative(long j);

    @DoNotProguard
    private native void setNetworkTypeNative(int i);

    @DoNotProguard
    private native void setTraceIdNative(String str);

    @DoNotProguard
    private native void setUploadLogStrategyNative(int i);

    @DoNotProguard
    private native int uploadLogFileNative(String str, String str2);

    @DoNotProguard
    public int debug(String str) {
        if (!this.e || this.mNativeHandler == 0) {
            return -1;
        }
        return logMessageNative(AlivcLogLevel.AlivcLogLevelDebug.getLevel(), str);
    }

    @DoNotProguard
    public int debug(String str, String str2) {
        if (!this.e || this.mNativeHandler == 0) {
            return -1;
        }
        return logMessageTagNative(AlivcLogLevel.AlivcLogLevelDebug.getLevel(), str, str2);
    }

    @DoNotProguard
    public void destory() {
        b();
        destoryNative();
        b.remove(Long.valueOf(this.d));
        this.c = null;
        this.f = null;
        this.mNativeHandler = 0L;
    }

    @DoNotProguard
    public void disableDebug() {
        if (this.mNativeHandler != 0) {
            this.e = false;
            disableLogNative();
        }
    }

    @DoNotProguard
    public int error(String str) {
        if (this.e && this.mNativeHandler != 0) {
            return logMessageNative(AlivcLogLevel.AlivcLogLevelError.getLevel(), str);
        }
        return -1;
    }

    @DoNotProguard
    public int error(String str, String str2) {
        if (this.e && this.mNativeHandler != 0) {
            return logMessageTagNative(AlivcLogLevel.AlivcLogLevelError.getLevel(), str, str2);
        }
        return -1;
    }

    @DoNotProguard
    public long getLogId() {
        if (this.mNativeHandler != 0) {
            return getLogIdNative();
        }
        return -1L;
    }

    @DoNotProguard
    public int info(String str) {
        if (!this.e || this.mNativeHandler == 0) {
            return -1;
        }
        return logMessageNative(AlivcLogLevel.AlivcLogLevelInfo.getLevel(), str);
    }

    @DoNotProguard
    public int info(String str, String str2) {
        if (!this.e || this.mNativeHandler == 0) {
            return -1;
        }
        return logMessageTagNative(AlivcLogLevel.AlivcLogLevelInfo.getLevel(), str, str2);
    }

    @DoNotProguard
    public void removeLogFileAfterUpload(boolean z) {
        if (this.mNativeHandler != 0) {
            removeLogFileAfterUploadNative(z);
        }
    }

    @DoNotProguard
    public int resetAcessTokenInfoWithKey(String str, String str2, String str3, String str4) {
        if (this.mNativeHandler != 0) {
            this.mAccessKey = str;
            this.mSecretKey = str2;
            this.mSecurityToken = str3;
            this.mExpireTime = str4;
            return resetAcessTokenInfoNative();
        }
        return -1;
    }

    @DoNotProguard
    public void saveCrashInfoToLog(String str, String str2) {
        if (this.mNativeHandler != 0) {
            saveCrashInfoToLogFileNative(str, str2);
        }
    }

    @DoNotProguard
    public void setAlivcLogListener(AlivcLogListener alivcLogListener) {
        this.f = alivcLogListener;
    }

    @DoNotProguard
    public void setFileMaxSize(int i) {
        if (this.mNativeHandler != 0) {
            setMaxFileSizeNative(i);
        }
    }

    @DoNotProguard
    public void setFilePath(String str) {
        if (this.mNativeHandler != 0) {
            setLogFileCachePathNative(str);
        }
    }

    @DoNotProguard
    public void setLogLevel(AlivcLogLevel alivcLogLevel) {
        if (this.mNativeHandler == 0 || alivcLogLevel == null) {
            return;
        }
        setLogLevelNative(alivcLogLevel.getLevel());
    }

    @DoNotProguard
    public void setLogMode(AlivcLogMode alivcLogMode) {
        if (this.mNativeHandler == 0 || alivcLogMode == null) {
            return;
        }
        setLogModeNative(alivcLogMode.getMode());
    }

    @DoNotProguard
    public void setTraceId(String str) {
        if (this.mNativeHandler != 0) {
            setTraceIdNative(str);
        }
    }

    @DoNotProguard
    public void uploadDefaultFile() {
        if (this.mNativeHandler != 0) {
            uploadLogFileNative(a, "");
        }
    }

    @DoNotProguard
    public void uploadFile(String str, String str2) {
        if (this.mNativeHandler != 0) {
            if (TextUtils.isEmpty(str)) {
                str = a;
            }
            if (str2 == null) {
                str2 = "";
            }
            uploadLogFileNative(str, str2);
        }
    }

    @DoNotProguard
    public int warn(String str) {
        if (!this.e || this.mNativeHandler == 0) {
            return -1;
        }
        return logMessageNative(AlivcLogLevel.AlivcLogLevelWarn.getLevel(), str);
    }

    @DoNotProguard
    public int warn(String str, String str2) {
        if (!this.e || this.mNativeHandler == 0) {
            return -1;
        }
        return logMessageTagNative(AlivcLogLevel.AlivcLogLevelWarn.getLevel(), str, str2);
    }
}
