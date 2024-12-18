package com.eclipsesource.v8.debug;

import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8Object;
public class EventData implements Releasable {
    public V8Object v8Object;

    public EventData(V8Object v8Object) {
        this.v8Object = v8Object.twin();
    }

    @Override
    public void close() {
        if (this.v8Object.isReleased()) {
            return;
        }
        this.v8Object.close();
    }

    @Override
    @Deprecated
    public void release() {
        close();
    }
}
