package com.alivc.conan;

import android.content.Context;
@DoNotProguard
public class AlivcConan {
    @DoNotProguard
    public static final String BUILD_ID = "0.9.5_11525459-SNAPSHOT";

    static {
        try {
            System.loadLibrary("alivc_conan");
        } catch (Throwable unused) {
        }
    }

    @DoNotProguard
    public static String getSDKVersion() {
        return "v0.9.5";
    }

    @DoNotProguard
    public static void initSDKContext(Context context) {
        a.setSDKContext(context);
    }
}
