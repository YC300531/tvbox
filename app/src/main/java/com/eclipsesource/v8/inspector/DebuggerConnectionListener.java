package com.eclipsesource.v8.inspector;
public interface DebuggerConnectionListener {
    void onDebuggerConnected();

    void onDebuggerDisconnected();
}
