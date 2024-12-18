package org.lsposed.hiddenapibypass;
public class HelperInvokeStub {
    private HelperInvokeStub(Object... objArr) {
        throw new IllegalStateException("Failed to new a instance");
    }

    private static Object invoke(Object... objArr) {
        throw new IllegalStateException("Failed to invoke the method");
    }
}
