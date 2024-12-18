package com.alivc.conan;
@DoNotProguard
public enum AlivcConanBusinessType {
    AlivcConanBusinessNone(0),
    AlivcConanBusinessSvideo(1),
    AlivcConanBusinessPusher(2),
    AlivcConanBusinessPlayer(3),
    AlivcConanBusinessIlive(4),
    AlivcConanBusinessWboard(5);
    
    private int a;

    @DoNotProguard
    AlivcConanBusinessType(int i) {
        this.a = i;
    }

    @DoNotProguard
    public int getBizType() {
        return this.a;
    }
}
