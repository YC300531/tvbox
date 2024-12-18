package org.conscrypt;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLSession;
abstract class AbstractConscryptEngine extends SSLEngine {
    public abstract byte[] exportKeyingMaterial(String str, byte[] bArr, int i);

    @Override
    public abstract String getApplicationProtocol();

    public abstract String[] getApplicationProtocols();

    public abstract byte[] getChannelId();

    @Override
    public abstract String getHandshakeApplicationProtocol();

    @Override
    public final SSLSession getHandshakeSession() {
        return handshakeSession();
    }

    public abstract String getHostname();

    @Override
    public abstract String getPeerHost();

    @Override
    public abstract int getPeerPort();

    public abstract byte[] getTlsUnique();

    public abstract SSLSession handshakeSession();

    public abstract int maxSealOverhead();

    public abstract void setApplicationProtocolSelector(ApplicationProtocolSelector applicationProtocolSelector);

    public abstract void setApplicationProtocols(String[] strArr);

    public abstract void setBufferAllocator(BufferAllocator bufferAllocator);

    public abstract void setChannelIdEnabled(boolean z);

    public abstract void setChannelIdPrivateKey(PrivateKey privateKey);

    public abstract void setHandshakeListener(HandshakeListener handshakeListener);

    public abstract void setHostname(String str);

    public abstract void setUseSessionTickets(boolean z);

    @Override
    public abstract SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2);

    @Override
    public abstract SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr);

    @Override
    public abstract SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i, int i2);

    public abstract SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer[] byteBufferArr2, int i3, int i4);

    public abstract SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2);

    @Override
    public abstract SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2);

    @Override
    public abstract SSLEngineResult wrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer byteBuffer);
}
