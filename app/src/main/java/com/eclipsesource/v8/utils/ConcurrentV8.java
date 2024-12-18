package com.eclipsesource.v8.utils;

import com.eclipsesource.v8.V8;
public final class ConcurrentV8 {
    private V8 v8;

    public ConcurrentV8() {
        this.v8 = null;
        V8 createV8Runtime = V8.createV8Runtime();
        this.v8 = createV8Runtime;
        createV8Runtime.getLocker().release();
    }

    public V8 getV8() {
        return this.v8;
    }

    public void release() {
        V8 v8 = this.v8;
        if (v8 == null || v8.isReleased()) {
            return;
        }
        run(new V8Runnable() {
            @Override
            public void run(V8 v82) {
                if (v82 == null || v82.isReleased()) {
                    return;
                }
                v82.close();
            }
        });
    }

    public synchronized void run(V8Runnable v8Runnable) {
        this.v8.getLocker().acquire();
        v8Runnable.run(this.v8);
        V8 v8 = this.v8;
        if (v8 != null && v8.getLocker() != null && this.v8.getLocker().hasLock()) {
            this.v8.getLocker().release();
        }
    }
}
