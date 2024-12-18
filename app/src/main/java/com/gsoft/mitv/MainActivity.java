package com.gsoft.mitv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.anymediacloud.iptv.standard.ForceTV;
import com.forcetech.service.PxpUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
public class MainActivity extends Service {
    private IBinder binder;
    private ForceTV forceTV;

    public MainActivity() {
        try {
            System.loadLibrary("mitv");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void checkLibrary() {
        if (new File(getCacheDir() + "/libmitv.so").exists()) {
            return;
        }
        String str = getCacheDir() + "/libmitv.so";
        try {
            InputStream open = getAssets().open("libmitv.so");
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (-1 == read) {
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
                fileOutputStream.write(bArr, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private native void loadLibrary(int i);

    @Override
    public IBinder onBind(Intent intent) {
        ForceTV forceTV = new ForceTV();
        this.forceTV = forceTV;
        forceTV.start(PxpUtil.MTV);
        return this.binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.binder = new LocalBinder();
        checkLibrary();
        loadLibrary(1);
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
