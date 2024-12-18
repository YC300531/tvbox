package com.alivc.conan.log;

import com.alivc.conan.DoNotProguard;
@DoNotProguard
public interface AlivcLogListener {
    @DoNotProguard
    void onAlivcLogCreateLogFileSuccess(AlivcLog alivcLog, String str);

    @DoNotProguard
    void onAlivcLogInitComplete(AlivcLog alivcLog);

    @DoNotProguard
    void onAlivcLogUploadLogFileSuccess(AlivcLog alivcLog, String str, String str2);

    @DoNotProguard
    void onLogErrorOccur(AlivcLog alivcLog, int i);

    @DoNotProguard
    void onStsExpired(AlivcLog alivcLog);

    @DoNotProguard
    void onStsWillExpireSoon(AlivcLog alivcLog, long j);
}
