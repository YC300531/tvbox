package com.github.tvbox.osc.ui.activity;

import androidx.base.k3.o;
import androidx.base.m3.j;
import androidx.media3.exoplayer.ExoPlayer;
import com.github.tvbox.osc.base.a;
import com.palayer.ku9.R;
public class CrashActivity extends a {
    public long e = 0;

    @Override
    public int c() {
        return R.layout.activity_crash;
    }

    @Override
    public void init() {
        if (isTaskRoot()) {
            new o(this).show();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - this.e < ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
            super.onBackPressed();
            System.exit(0);
            return;
        }
        this.e = System.currentTimeMillis();
        j.a("再按一次返回键退出程序", j.c);
    }

    @Override
    public void onResume() {
        super.onResume();
        e(false);
    }
}
