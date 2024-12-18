package com.east.android.zlive;

import com.github.tvbox.osc.base.App;
import java.io.File;
public class FileUtil {
    private static final String TAG = "FileUtil";

    public static File getCacheDir() {
        return App.c.getCacheDir();
    }

    public static String getCachePath() {
        return getCacheDir().getAbsolutePath();
    }
}
