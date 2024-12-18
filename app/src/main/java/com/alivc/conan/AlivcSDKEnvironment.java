package com.alivc.conan;
@DoNotProguard
public enum AlivcSDKEnvironment {
    ENV_DAILY(1),
    ENV_PRE(2),
    EN_ONLINE(3);
    
    private int a;

    @DoNotProguard
    AlivcSDKEnvironment(int i) {
        this.a = i;
    }

    @DoNotProguard
    public int getSDKEnv() {
        return this.a;
    }
}
