package com.eclipsesource.v8.inspector;
public interface V8InspectorDelegate {
    void onResponse(String str);

    void waitFrontendMessageOnPause();
}
