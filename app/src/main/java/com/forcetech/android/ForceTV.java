package com.forcetech.android;
public class ForceTV {
    public native int start(int i, int i2);

    public void start(String str, int i) {
        try {
            System.loadLibrary(str);
            start(i, 20971520);
        } catch (Throwable unused) {
        }
    }

    public native int stop();
}
