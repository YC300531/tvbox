package org.conscrypt;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
final class Java8EngineWrapper extends AbstractConscryptEngine {
    private final ConscryptEngine delegate;
    private BiFunction<SSLEngine, List<String>, String> selector;

    public Java8EngineWrapper(ConscryptEngine conscryptEngine) {
        this.delegate = (ConscryptEngine) Preconditions.checkNotNull(conscryptEngine, "delegate");
    }

    public static SSLEngine getDelegate(SSLEngine sSLEngine) {
        return sSLEngine instanceof Java8EngineWrapper ? ((Java8EngineWrapper) sSLEngine).delegate : sSLEngine;
    }

    private static ApplicationProtocolSelector toApplicationProtocolSelector(final BiFunction<SSLEngine, List<String>, String> biFunction) {
        if (biFunction == null) {
            return null;
        }
        return new ApplicationProtocolSelector() {
            @Override
            public String selectApplicationProtocol(SSLEngine sSLEngine, List<String> list) {
                return (String) biFunction.apply(sSLEngine, list);
            }

            @Override
            public String selectApplicationProtocol(SSLSocket sSLSocket, List<String> list) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public void beginHandshake() {
        this.delegate.beginHandshake();
    }

    @Override
    public void closeInbound() {
        this.delegate.closeInbound();
    }

    @Override
    public void closeOutbound() {
        this.delegate.closeOutbound();
    }

    @Override
    public byte[] exportKeyingMaterial(String str, byte[] bArr, int i) {
        return this.delegate.exportKeyingMaterial(str, bArr, i);
    }

    @Override
    public String getApplicationProtocol() {
        return this.delegate.getApplicationProtocol();
    }

    @Override
    public String[] getApplicationProtocols() {
        return this.delegate.getApplicationProtocols();
    }

    @Override
    public byte[] getChannelId() {
        return this.delegate.getChannelId();
    }

    @Override
    public Runnable getDelegatedTask() {
        return this.delegate.getDelegatedTask();
    }

    @Override
    public boolean getEnableSessionCreation() {
        return this.delegate.getEnableSessionCreation();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return this.delegate.getEnabledCipherSuites();
    }

    @Override
    public String[] getEnabledProtocols() {
        return this.delegate.getEnabledProtocols();
    }

    @Override
    public String getHandshakeApplicationProtocol() {
        return this.delegate.getHandshakeApplicationProtocol();
    }

    @Override
    public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return this.selector;
    }

    @Override
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return this.delegate.getHandshakeStatus();
    }

    @Override
    public String getHostname() {
        return this.delegate.getHostname();
    }

    @Override
    public boolean getNeedClientAuth() {
        return this.delegate.getNeedClientAuth();
    }

    @Override
    public String getPeerHost() {
        return this.delegate.getPeerHost();
    }

    @Override
    public int getPeerPort() {
        return this.delegate.getPeerPort();
    }

    @Override
    public SSLParameters getSSLParameters() {
        return this.delegate.getSSLParameters();
    }

    @Override
    public SSLSession getSession() {
        return this.delegate.getSession();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return this.delegate.getSupportedCipherSuites();
    }

    @Override
    public String[] getSupportedProtocols() {
        return this.delegate.getSupportedProtocols();
    }

    @Override
    public byte[] getTlsUnique() {
        return this.delegate.getTlsUnique();
    }

    @Override
    public boolean getUseClientMode() {
        return this.delegate.getUseClientMode();
    }

    @Override
    public boolean getWantClientAuth() {
        return this.delegate.getWantClientAuth();
    }

    @Override
    public SSLSession handshakeSession() {
        return this.delegate.handshakeSession();
    }

    @Override
    public boolean isInboundDone() {
        return this.delegate.isInboundDone();
    }

    @Override
    public boolean isOutboundDone() {
        return this.delegate.isOutboundDone();
    }

    @Override
    public int maxSealOverhead() {
        return this.delegate.maxSealOverhead();
    }

    @Override
    public void setApplicationProtocolSelector(ApplicationProtocolSelector applicationProtocolSelector) {
        this.delegate.setApplicationProtocolSelector(applicationProtocolSelector == null ? null : new ApplicationProtocolSelectorAdapter(this, applicationProtocolSelector));
    }

    @Override
    public void setApplicationProtocols(String[] strArr) {
        this.delegate.setApplicationProtocols(strArr);
    }

    @Override
    public void setBufferAllocator(BufferAllocator bufferAllocator) {
        this.delegate.setBufferAllocator(bufferAllocator);
    }

    @Override
    public void setChannelIdEnabled(boolean z) {
        this.delegate.setChannelIdEnabled(z);
    }

    @Override
    public void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.delegate.setChannelIdPrivateKey(privateKey);
    }

    @Override
    public void setEnableSessionCreation(boolean z) {
        this.delegate.setEnableSessionCreation(z);
    }

    @Override
    public void setEnabledCipherSuites(String[] strArr) {
        this.delegate.setEnabledCipherSuites(strArr);
    }

    @Override
    public void setEnabledProtocols(String[] strArr) {
        this.delegate.setEnabledProtocols(strArr);
    }

    @Override
    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> biFunction) {
        this.selector = biFunction;
        setApplicationProtocolSelector(toApplicationProtocolSelector(biFunction));
    }

    @Override
    public void setHandshakeListener(HandshakeListener handshakeListener) {
        this.delegate.setHandshakeListener(handshakeListener);
    }

    @Override
    public void setHostname(String str) {
        this.delegate.setHostname(str);
    }

    @Override
    public void setNeedClientAuth(boolean z) {
        this.delegate.setNeedClientAuth(z);
    }

    @Override
    public void setSSLParameters(SSLParameters sSLParameters) {
        this.delegate.setSSLParameters(sSLParameters);
    }

    @Override
    public void setUseClientMode(boolean z) {
        this.delegate.setUseClientMode(z);
    }

    @Override
    public void setUseSessionTickets(boolean z) {
        this.delegate.setUseSessionTickets(z);
    }

    @Override
    public void setWantClientAuth(boolean z) {
        this.delegate.setWantClientAuth(z);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        return this.delegate.unwrap(byteBuffer, byteBuffer2);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr) {
        return this.delegate.unwrap(byteBuffer, byteBufferArr);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i, int i2) {
        return this.delegate.unwrap(byteBuffer, byteBufferArr, i, i2);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer[] byteBufferArr2, int i3, int i4) {
        return this.delegate.unwrap(byteBufferArr, i, i2, byteBufferArr2, i3, i4);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2) {
        return this.delegate.unwrap(byteBufferArr, byteBufferArr2);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        return this.delegate.wrap(byteBuffer, byteBuffer2);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer byteBuffer) {
        return this.delegate.wrap(byteBufferArr, i, i2, byteBuffer);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] byteBufferArr, ByteBuffer byteBuffer) {
        return this.delegate.wrap(byteBufferArr, byteBuffer);
    }
}
