package com.alivc.conan.log;

import com.alivc.conan.DoNotProguard;
@DoNotProguard
public enum AlivcLogLevel {
    AlivcLogLevelDebug(0),
    AlivcLogLevelInfo(1),
    AlivcLogLevelWarn(2),
    AlivcLogLevelError(3),
    AlivcLogLevelFatal(4);
    
    private int a;

    AlivcLogLevel(int i) {
        this.a = i;
    }

    @DoNotProguard
    public int getLevel() {
        return this.a;
    }
}
