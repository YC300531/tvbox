package com.alivc.conan.event;

import com.alivc.conan.AlivcConanBusinessType;
import com.alivc.conan.AlivcSDKEnvironment;
import com.alivc.conan.DoNotProguard;
@DoNotProguard
public class AlivcEventReporterConfig {
    private String a;
    private String b;
    private String c;
    private boolean d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private AlivcConanBusinessType k;
    private AlivcSDKEnvironment l;

    @DoNotProguard
    public String getAccessKey() {
        return this.f;
    }

    @DoNotProguard
    public String getApplicationName() {
        return this.j;
    }

    @DoNotProguard
    public AlivcConanBusinessType getBusinessType() {
        AlivcConanBusinessType alivcConanBusinessType = this.k;
        return alivcConanBusinessType == null ? AlivcConanBusinessType.AlivcConanBusinessNone : alivcConanBusinessType;
    }

    @DoNotProguard
    public String getEndPoint() {
        return this.e;
    }

    @DoNotProguard
    public String getExpireTime() {
        return this.i;
    }

    @DoNotProguard
    public String getHost() {
        return this.a;
    }

    @DoNotProguard
    public String getLogStore() {
        return this.c;
    }

    @DoNotProguard
    public String getProjectName() {
        return this.b;
    }

    @DoNotProguard
    public AlivcSDKEnvironment getSDKEnvironment() {
        return this.l;
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
    public boolean isUseExternalAuth() {
        return this.d;
    }

    @DoNotProguard
    public void setAccessKey(String str) {
        this.f = str;
    }

    @DoNotProguard
    public void setApplicationName(String str) {
        this.j = str;
    }

    @DoNotProguard
    public void setBusinessType(AlivcConanBusinessType alivcConanBusinessType) {
        this.k = alivcConanBusinessType;
    }

    @DoNotProguard
    public void setEndPoint(String str) {
        this.e = str;
    }

    @DoNotProguard
    public void setExpireTime(String str) {
        this.i = str;
    }

    @DoNotProguard
    public void setHost(String str) {
        this.a = str;
    }

    @DoNotProguard
    public void setLogStore(String str) {
        this.c = str;
    }

    @DoNotProguard
    public void setProjectName(String str) {
        this.b = str;
    }

    @DoNotProguard
    public void setSDKEnvironment(AlivcSDKEnvironment alivcSDKEnvironment) {
        this.l = alivcSDKEnvironment;
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
    public void setUseExternalAuth(boolean z) {
        this.d = z;
    }
}
