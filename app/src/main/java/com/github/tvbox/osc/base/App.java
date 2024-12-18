package com.github.tvbox.osc.base;

import androidx.base.m3.b;
import androidx.base.n3.c;
import androidx.base.n3.d;
import androidx.multidex.MultiDexApplication;
import com.github.tvbox.osc.ui.activity.LoginActivity;
import com.orhanobut.hawk.Hawk;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
public class App extends MultiDexApplication implements Runnable {
    public static App c;

    @Override
    public void onCreate() {
        super.onCreate();
        c = this;
        AutoSizeConfig.getInstance().setCustomFragment(true).getUnitsManager().setSupportDP(true).setSupportSP(false).setSupportSubunits(Subunits.MM);
        androidx.base.h3.a.c();
        LoginActivity.i = new b();
        androidx.base.h3.a.a(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void run() {
        c.a(this, ((Integer) Hawk.get(IjkMediaMeta.IJKM_KEY_LANGUAGE, 0)).intValue() == 0 ? "zh" : "");
        new androidx.base.b3.a().a(this);
        d.c();
        androidx.base.g3.a.c(this);
        androidx.base.e3.d.a();
    }
}
