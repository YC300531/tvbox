package com.forcetech.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.forcetech.android.ForceTV;
import com.gsoft.mitv.LocalBinder;
public abstract class PxPService extends Service {
    private IBinder binder;
    private ForceTV forceTV;

    public abstract int getPort();

    @Override
    public IBinder onBind(Intent intent) {
        ForceTV forceTV = new ForceTV();
        this.forceTV = forceTV;
        forceTV.start(intent.getStringExtra("scheme"), getPort());
        return this.binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.binder = new LocalBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        ForceTV forceTV = this.forceTV;
        if (forceTV != null) {
            forceTV.stop();
        }
        return super.onUnbind(intent);
    }
}
