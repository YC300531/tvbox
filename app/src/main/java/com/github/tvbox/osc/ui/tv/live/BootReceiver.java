package com.github.tvbox.osc.ui.tv.live;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.github.tvbox.osc.ui.activity.LoginActivity;
import com.orhanobut.hawk.Hawk;
import java.util.Objects;
public class BootReceiver extends BroadcastReceiver {
    public static boolean b;
    public Handler a = new Handler();

    public class a implements Runnable {
        public final Context c;

        public a(Context context) {
            this.c = context;
        }

        @Override
        public void run() {
            BootReceiver bootReceiver = BootReceiver.this;
            Context context = this.c;
            Objects.requireNonNull(bootReceiver);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            BootReceiver.b = true;
        }
    }

    @Override
    @SuppressLint({"UnsafeProtectedBroadcastReceiver"})
    public void onReceive(Context context, Intent intent) {
        if (!((Boolean) Hawk.get("boot_start", Boolean.FALSE)).booleanValue() || b) {
            return;
        }
        this.a.postDelayed(new a(context), 5000L);
    }
}
