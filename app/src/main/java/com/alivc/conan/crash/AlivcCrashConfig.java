package com.alivc.conan.crash;

import com.alivc.conan.DoNotProguard;
@DoNotProguard
public class AlivcCrashConfig {
    private long a;
    private long b;
    private String c;
    private String d;
    private String e;

    @DoNotProguard
    public long getAlivcLogId() {
        return this.b;
    }

    @DoNotProguard
    public String getCrashFileSavePath() {
        return this.c;
    }

    @DoNotProguard
    public String getCrashFilter() {
        return this.e;
    }

    @DoNotProguard
    public String getCrashTraceId() {
        return this.d;
    }

    @DoNotProguard
    public long getEventReportId() {
        return this.a;
    }

    @DoNotProguard
    public void setAlivcLogId(long j) {
        this.b = j;
    }

    @DoNotProguard
    public void setCrashFileSavePath(String str) {
        this.c = str;
    }

    @DoNotProguard
    public void setCrashFilter(String str) {
        this.e = str;
    }

    @DoNotProguard
    public void setCrashTraceId(String str) {
        this.d = str;
    }

    @DoNotProguard
    public void setEventReportId(long j) {
        this.a = j;
    }
}
