package com.eclipsesource.v8.utils;

import com.eclipsesource.v8.V8;
public class V8Thread extends Thread {
    private V8 runtime;
    private final V8Runnable target;

    public V8Thread(V8Runnable v8Runnable) {
        this.target = v8Runnable;
    }

    @Override
    public void run() {
        V8 createV8Runtime = V8.createV8Runtime();
        this.runtime = createV8Runtime;
        try {
            this.target.run(createV8Runtime);
            synchronized (this) {
                if (this.runtime.getLocker().hasLock()) {
                    this.runtime.close();
                    this.runtime = null;
                }
            }
        } catch (Throwable th) {
            synchronized (this) {
                if (this.runtime.getLocker().hasLock()) {
                    this.runtime.close();
                    this.runtime = null;
                }
                throw th;
            }
        }
    }
}
