package com.alivc.conan.event;

import android.text.TextUtils;
import androidx.base.a.b;
import androidx.media3.common.PlaybackException;
import androidx.media3.exoplayer.upstream.CmcdData;
import com.alivc.conan.AlivcSDKEnvironment;
import com.alivc.conan.DoNotProguard;
import java.util.HashMap;
import java.util.Map;
@DoNotProguard
public class AlivcEventReporter {
    private static final Map<Long, AlivcEventReporter> a = new HashMap();
    private long b;
    private String c;
    @DoNotProguard
    private String mAccessKey;
    @DoNotProguard
    private int mBussinessType;
    @DoNotProguard
    private String mClientID;
    @DoNotProguard
    private String mDeviceModel;
    @DoNotProguard
    private String mEndPoint;
    @DoNotProguard
    private String mExpireTime;
    @DoNotProguard
    private String mLogStore;
    @DoNotProguard
    private String mProjectName;
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
    private AlivcEventReporterListener d = null;

    @DoNotProguard
    public AlivcEventReporter(AlivcEventReporterConfig alivcEventReporterConfig) {
        if (com.alivc.conan.a.a() == null) {
            throw new IllegalArgumentException("Context must NOT null, check setSDKContext");
        }
        if (alivcEventReporterConfig == null) {
            throw new IllegalArgumentException("Unable to initialize the AlivcEventReporter with empty configuration!");
        }
        try {
            this.mClientID = com.alivc.conan.a.b();
            this.mTerminalType = com.alivc.conan.a.c();
            this.mDeviceModel = com.alivc.conan.a.n();
            this.mBussinessType = alivcEventReporterConfig.getBusinessType().getBizType();
            this.mUseExternalAuth = alivcEventReporterConfig.isUseExternalAuth();
            this.mEndPoint = alivcEventReporterConfig.getEndPoint();
            this.mProjectName = alivcEventReporterConfig.getProjectName();
            this.mLogStore = alivcEventReporterConfig.getLogStore();
            this.mAccessKey = alivcEventReporterConfig.getAccessKey();
            this.mSecretKey = alivcEventReporterConfig.getSecretKey();
            this.mSecurityToken = alivcEventReporterConfig.getSecurityToken();
            this.mExpireTime = alivcEventReporterConfig.getExpireTime();
            initNative();
            a.a().a(alivcEventReporterConfig.getApplicationName());
            a.a().a(alivcEventReporterConfig.getSDKEnvironment());
            if (this.mNativeHandler != 0) {
                long idNative = getIdNative();
                if (idNative != -1) {
                    this.b = idNative;
                    a.put(Long.valueOf(idNative), this);
                }
                setPublicParamNative(a(a.a().c()));
            }
        } catch (Throwable unused) {
        }
    }

    private String a(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (!z) {
                    sb.append(" *||* ");
                }
                sb.append(entry.getKey());
                sb.append(" * ");
                sb.append(entry.getValue());
                z = false;
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    @DoNotProguard
    private native void destoryNative();

    @DoNotProguard
    private native int flushEventNative(int i, String str);

    @DoNotProguard
    public static AlivcEventReporter getEventReporterById(long j) {
        return a.get(Long.valueOf(j));
    }

    @DoNotProguard
    private native long getIdNative();

    @DoNotProguard
    private native void initNative();

    @DoNotProguard
    private void onEventReportErrorOccur(int i) {
        AlivcEventReporterListener alivcEventReporterListener = this.d;
        if (alivcEventReporterListener != null) {
            alivcEventReporterListener.onEventReportErrorOccur(this, i);
        }
    }

    @DoNotProguard
    private void onStsExpired() {
        AlivcEventReporterListener alivcEventReporterListener = this.d;
        if (alivcEventReporterListener != null) {
            alivcEventReporterListener.OnStsExpired(this);
        }
    }

    @DoNotProguard
    private void onStsWillExpireSoon(long j) {
        AlivcEventReporterListener alivcEventReporterListener = this.d;
        if (alivcEventReporterListener != null) {
            alivcEventReporterListener.onStsWillExpireSoon(this, j);
        }
    }

    @DoNotProguard
    private native int resetAcessTokenInfoNative();

    @DoNotProguard
    private native int sendEventNative(int i, String str);

    @DoNotProguard
    private native int setPublicParamNative(String str);

    @DoNotProguard
    private native int updatePublicParamNative(String str, String str2);

    public int a(int i, Map<String, String> map) {
        if (this.mNativeHandler != 0) {
            return flushEventNative(i, a(map));
        }
        return -1;
    }

    @DoNotProguard
    public void destory() {
        destoryNative();
        a.remove(Long.valueOf(this.b));
        this.d = null;
        this.mNativeHandler = 0L;
        this.mNativeCallback = 0L;
    }

    @DoNotProguard
    public long getEventReporterId() {
        if (this.mNativeHandler != 0) {
            return getIdNative();
        }
        return -1L;
    }

    @DoNotProguard
    public String refreshSessionId() {
        String d = com.alivc.conan.a.d();
        setSessionId(d);
        return d;
    }

    @DoNotProguard
    public void resetAcessTokenInfoWithKey(String str, String str2, String str3, String str4) {
        if (this.mNativeHandler != 0) {
            this.mAccessKey = str;
            this.mSecretKey = str2;
            this.mSecurityToken = str3;
            this.mExpireTime = str4;
            resetAcessTokenInfoNative();
        }
    }

    @DoNotProguard
    public int sendCyclistEvent() {
        if (this.mNativeHandler != 0) {
            return sendEventNative(PlaybackException.ERROR_CODE_DRM_PROVISIONING_FAILED, a(a.a().b()));
        }
        return -1;
    }

    @DoNotProguard
    public int sendEvent(int i, Map<String, String> map) {
        if (this.mNativeHandler != 0) {
            return sendEventNative(i, a(map));
        }
        return -1;
    }

    @DoNotProguard
    public int sendOnceEvent() {
        if (this.mNativeHandler != 0) {
            Map<String, String> d = a.a().d();
            if (!TextUtils.isEmpty(this.c)) {
                d.put("bcid", this.c);
            }
            return sendEventNative(PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED, a(d));
        }
        return -1;
    }

    @DoNotProguard
    public void setAlivcRole(String str) {
        if (this.mNativeHandler == 0 || TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative("role", str);
    }

    @DoNotProguard
    public void setAliyunAppId(String str) {
        if (this.mNativeHandler == 0 || TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative("ali_app_id", str);
    }

    @DoNotProguard
    public void setApplicationName(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        a.a().a(str);
    }

    @DoNotProguard
    public void setBuildIdCommitIdString(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.c = str;
    }

    @DoNotProguard
    public void setEventReportListener(AlivcEventReporterListener alivcEventReporterListener) {
        this.d = alivcEventReporterListener;
    }

    @DoNotProguard
    public void setSDKEnvironment(AlivcSDKEnvironment alivcSDKEnvironment) {
        if (this.mNativeHandler == 0 || alivcSDKEnvironment == null) {
            return;
        }
        StringBuilder d = b.d("");
        d.append(alivcSDKEnvironment.getSDKEnv());
        updatePublicParamNative("se", d.toString());
    }

    @DoNotProguard
    public void setSDKVersion(String str) {
        if (this.mNativeHandler == 0 || TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative(CmcdData.Factory.OBJECT_TYPE_MUXED_AUDIO_AND_VIDEO, str);
    }

    @DoNotProguard
    public void setSessionId(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative("ri", str);
    }

    @DoNotProguard
    public void setSubModuleName(String str) {
        if (this.mNativeHandler == 0 || TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative("md", str);
    }

    @DoNotProguard
    public void updateRoomId(String str) {
        if (this.mNativeHandler == 0 || TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative("rid", str);
    }

    @DoNotProguard
    public void updateTraceId(String str) {
        if (this.mNativeHandler == 0 || TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative("ti", str);
    }

    @DoNotProguard
    public void updateUserId(String str) {
        if (this.mNativeHandler == 0 || TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative("uid", str);
    }

    @DoNotProguard
    public void updateVideoType(String str) {
        if (this.mNativeHandler == 0 || TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative("vt", str);
    }

    @DoNotProguard
    public void updateVideoUrl(String str) {
        if (this.mNativeHandler == 0 || TextUtils.isEmpty(str)) {
            return;
        }
        updatePublicParamNative("vu", str);
    }
}
