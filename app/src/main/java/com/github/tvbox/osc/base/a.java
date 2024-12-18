package com.github.tvbox.osc.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.base.n3.c;
import com.github.tvbox.osc.Rstyleable;
import com.orhanobut.hawk.Hawk;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.internal.CustomAdapt;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
public abstract class a extends AppCompatActivity implements CustomAdapt {
    public static float d = -100.0f;
    public Context c;

    @Override
    public void attachBaseContext(Context context) {
        SharedPreferences defaultSharedPreferences;
        String str;
        if (((Integer) Hawk.get(IjkMediaMeta.IJKM_KEY_LANGUAGE, 0)).intValue() == 0) {
            defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            str = "zh";
        } else {
            defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            str = "";
        }
        super.attachBaseContext(c.a(context, defaultSharedPreferences.getString("Locale.Helper.Selected.Language", str)));
    }

    public abstract int c();

    public int d() {
        return this.c.obtainStyledAttributes(Rstyleable.themeColor).getColor(0, 0);
    }

    public void e(boolean z) {
        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility() | 256 | 1 | 4 | 2048 | 4096;
        if (!z) {
            systemUiVisibility = systemUiVisibility | 2 | 512;
        }
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }

    public boolean f() {
        return Build.VERSION.SDK_INT >= 26;
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        try {
            AutoSizeCompat.autoConvertDensityOfGlobal(resources);
        } catch (Exception unused) {
        }
        return resources;
    }

    @Override
    public float getSizeInDp() {
        return isBaseOnWidth() ? 1280.0f : 720.0f;
    }

    public abstract void init();

    @Override
    public boolean isBaseOnWidth() {
        return d < 4.0f;
    }

    @Override
    public void onCreate(@androidx.annotation.Nullable android.os.Bundle r6) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.github.tvbox.osc.base.a.onCreate(android.os.Bundle):void");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        e(true);
    }
}
