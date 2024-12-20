package org.conscrypt;

import javax.crypto.ShortBufferException;
final class ShortBufferWithoutStackTraceException extends ShortBufferException {
    private static final long serialVersionUID = 676150236007842683L;

    public ShortBufferWithoutStackTraceException() {
    }

    public ShortBufferWithoutStackTraceException(String str) {
        super(str);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
