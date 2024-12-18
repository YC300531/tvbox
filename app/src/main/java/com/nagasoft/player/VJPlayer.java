package com.nagasoft.player;

import android.util.Log;
import androidx.base.a.b;
import java.lang.ref.WeakReference;
public class VJPlayer {
    public static final int PLAYER_NOTIFY_URL = 4113;
    public static final String TAG = "NGPlayer";
    private static boolean gbload;
    private String mStrVideoURL;
    private UrlChanged mUCCallback;
    private final int mNativeListener = 0;
    private final int mNativePlayer = 0;
    private OnVJMSErrorListener mVELCallback = null;

    static {
        try {
            System.loadLibrary("p2pcore");
            System.loadLibrary("vjplayer_jni");
            gbload = true;
            native_init();
        } catch (Throwable th) {
            gbload = false;
            StringBuilder d = b.d("loadLibrary libvjplayer_jni error: ");
            d.append(th.toString());
            Log.e(TAG, d.toString(), th);
            th.printStackTrace();
        }
    }

    public VJPlayer(UrlChanged urlChanged) {
        this.mUCCallback = null;
        if (gbload) {
            native_setup(new WeakReference(this));
        }
        this.mUCCallback = urlChanged;
    }

    public static final native void native_init();

    public native void _release();

    public native String getMac();

    public native long getPlayBackDuration();

    public String getVideoURL() {
        return this.mStrVideoURL;
    }

    public native boolean isLiveStream();

    public native boolean isPlayBackStream();

    public native boolean isVodFile();

    public final native void native_finalize();

    public final native void native_setup(Object obj);

    public void notifyError(int i) {
        Log.d(TAG, "from native error is " + i);
        notifyUIError(i);
    }

    public void notifyPlayURL(String str) {
        Log.d(TAG, "from native string is " + str);
        this.mStrVideoURL = str;
        notifyUI(str);
    }

    public void notifyUI(String str) {
        UrlChanged urlChanged = this.mUCCallback;
        if (urlChanged != null) {
            urlChanged.onUrlChanged(str);
        }
    }

    public void notifyUIError(int i) {
        OnVJMSErrorListener onVJMSErrorListener = this.mVELCallback;
        if (onVJMSErrorListener != null) {
            onVJMSErrorListener.onVJMSError(i);
        }
    }

    public native boolean seekPlayBack(long j);

    public void setOnVJMSErrorListener(OnVJMSErrorListener onVJMSErrorListener) {
        this.mVELCallback = onVJMSErrorListener;
    }

    public native void setURL(String str);

    public native void setVJMSBufferTimeout(int i);

    public native boolean start();

    public native void stop();
}
