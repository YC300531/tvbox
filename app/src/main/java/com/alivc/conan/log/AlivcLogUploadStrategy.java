package com.alivc.conan.log;

import com.alivc.conan.DoNotProguard;
@DoNotProguard
public enum AlivcLogUploadStrategy {
    AlivcLogUploadStrategyAll(0),
    AlivcLogUploadStrategyWifi(1);
    
    private int a;

    AlivcLogUploadStrategy(int i) {
        this.a = i;
    }

    @DoNotProguard
    public int getStrategy() {
        return this.a;
    }
}
