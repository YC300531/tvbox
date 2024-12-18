package com.east.android.zlive;

import androidx.base.a.b;
public class ZLives {
    private static final int PORT = 6677;
    private final String BASE = "http://127.0.0.1:6677/stream/";
    private boolean init;

    public static class Loader {
        public static volatile ZLives INSTANCE = new ZLives();

        private Loader() {
        }
    }

    public static ZLives get() {
        return Loader.INSTANCE;
    }

    private String getLive(String str) {
        return b.b("http://127.0.0.1:6677/stream/live?uuid=", str);
    }

    private String getOpen(String str) {
        return b.b("http://127.0.0.1:6677/stream/open?uuid=", str);
    }

    public String fetch(String str) {
        String str2;
        if (!this.init) {
            init();
        }
        String[] split = str.split("/");
        String str3 = split[2];
        String str4 = getLive(str2) + "&server=" + str3 + "&group=5850&mac=00:00:00:00:00:00&dir=" + FileUtil.getCacheDir();
        new Thread(new androidx.base.o3.b(getOpen(split[3]))).start();
        return str4;
    }

    public void init() {
        ZLive.INSTANCE.OnLiveStart(6677L);
        this.init = true;
    }

    public void stop() {
        try {
            if (this.init) {
                ZLive.INSTANCE.OnLiveStop();
            }
            this.init = false;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
