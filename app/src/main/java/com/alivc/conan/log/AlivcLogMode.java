package com.alivc.conan.log;

import com.alivc.conan.DoNotProguard;
@DoNotProguard
public enum AlivcLogMode {
    AlivcLogModeLocalPrint(1),
    AlivcLogModeLocalFile(2),
    AlivcLogModeFileAndPrint(3);
    
    private int a;

    AlivcLogMode(int i) {
        this.a = i;
    }

    @DoNotProguard
    public int getMode() {
        return this.a;
    }
}
