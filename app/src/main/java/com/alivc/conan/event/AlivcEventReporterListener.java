package com.alivc.conan.event;

import com.alivc.conan.DoNotProguard;
@DoNotProguard
public interface AlivcEventReporterListener {
    @DoNotProguard
    void OnStsExpired(AlivcEventReporter alivcEventReporter);

    @DoNotProguard
    void onEventReportErrorOccur(AlivcEventReporter alivcEventReporter, int i);

    @DoNotProguard
    void onStsWillExpireSoon(AlivcEventReporter alivcEventReporter, long j);
}
