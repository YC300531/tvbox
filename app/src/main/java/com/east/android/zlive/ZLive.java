package com.east.android.zlive;

import com.sun.jna.Library;
import com.sun.jna.Native;
public interface ZLive extends Library {
    public static final ZLive INSTANCE = (ZLive) Native.load("core", ZLive.class);

    void OnLiveStart(long j);

    void OnLiveStop();
}
