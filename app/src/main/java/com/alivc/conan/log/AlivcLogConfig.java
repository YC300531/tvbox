package com.alivc.conan.log;

import com.alivc.conan.AlivcConanBusinessType;
import com.alivc.conan.DoNotProguard;
@DoNotProguard
public class AlivcLogConfig {
    private AlivcLogUploadStrategy a;
    private AlivcConanBusinessType b;
    private boolean c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;

    @DoNotProguard
    public String getAccessKey() {
        return this.f;
    }

    @DoNotProguard
    public String getBucket() {
        return this.e;
    }

    @DoNotProguard
    public AlivcConanBusinessType getBusinessType() {
        AlivcConanBusinessType alivcConanBusinessType = this.b;
        return alivcConanBusinessType == null ? AlivcConanBusinessType.AlivcConanBusinessNone : alivcConanBusinessType;
    }

    @DoNotProguard
    public String getEndPoint() {
        return this.d;
    }

    @DoNotProguard
    public String getExpireTime() {
        return this.i;
    }

    @DoNotProguard
    public String getSecretKey() {
        return this.g;
    }

    @DoNotProguard
    public String getSecurityToken() {
        return this.h;
    }

    @DoNotProguard
    public AlivcLogUploadStrategy getStrategy() {
        return this.a;
    }

    @DoNotProguard
    public boolean isUseExternalAuth() {
        return this.c;
    }

    @DoNotProguard
    public void setAccessKey(String str) {
        this.f = str;
    }

    @DoNotProguard
    public void setBucket(String str) {
        this.e = str;
    }

    @DoNotProguard
    public void setBusinessType(AlivcConanBusinessType alivcConanBusinessType) {
        this.b = alivcConanBusinessType;
    }

    @DoNotProguard
    public void setEndPoint(String str) {
        this.d = str;
    }

    @DoNotProguard
    public void setExpireTime(String str) {
        this.i = str;
    }

    @DoNotProguard
    public void setSecretKey(String str) {
        this.g = str;
    }

    @DoNotProguard
    public void setSecurityToken(String str) {
        this.h = str;
    }

    @DoNotProguard
    public void setStrategy(AlivcLogUploadStrategy alivcLogUploadStrategy) {
        this.a = alivcLogUploadStrategy;
    }

    @DoNotProguard
    public void setUseExternalAuth(boolean z) {
        this.c = z;
    }
}
