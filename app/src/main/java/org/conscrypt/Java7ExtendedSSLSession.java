package org.conscrypt;

import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
class Java7ExtendedSSLSession extends ExtendedSSLSession implements ConscryptSession {
    private static final String[] LOCAL_SUPPORTED_SIGNATURE_ALGORITHMS = {"SHA512withRSA", "SHA512withECDSA", "SHA384withRSA", "SHA384withECDSA", "SHA256withRSA", "SHA256withECDSA", "SHA224withRSA", "SHA224withECDSA", "SHA1withRSA", "SHA1withECDSA"};
    private static final String[] PEER_SUPPORTED_SIGNATURE_ALGORITHMS = {"SHA1withRSA", "SHA1withECDSA"};
    public final ExternalSession delegate;

    public Java7ExtendedSSLSession(ExternalSession externalSession) {
        this.delegate = externalSession;
    }

    @Override
    public final int getApplicationBufferSize() {
        return this.delegate.getApplicationBufferSize();
    }

    @Override
    public String getApplicationProtocol() {
        return this.delegate.getApplicationProtocol();
    }

    @Override
    public final String getCipherSuite() {
        return this.delegate.getCipherSuite();
    }

    @Override
    public final long getCreationTime() {
        return this.delegate.getCreationTime();
    }

    @Override
    public final byte[] getId() {
        return this.delegate.getId();
    }

    @Override
    public final long getLastAccessedTime() {
        return this.delegate.getLastAccessedTime();
    }

    @Override
    public final Certificate[] getLocalCertificates() {
        return this.delegate.getLocalCertificates();
    }

    @Override
    public final Principal getLocalPrincipal() {
        return this.delegate.getLocalPrincipal();
    }

    @Override
    public final String[] getLocalSupportedSignatureAlgorithms() {
        return (String[]) LOCAL_SUPPORTED_SIGNATURE_ALGORITHMS.clone();
    }

    @Override
    public final int getPacketBufferSize() {
        return this.delegate.getPacketBufferSize();
    }

    @Override
    public final X509Certificate[] getPeerCertificateChain() {
        return this.delegate.getPeerCertificateChain();
    }

    @Override
    public java.security.cert.X509Certificate[] getPeerCertificates() {
        return this.delegate.getPeerCertificates();
    }

    @Override
    public final String getPeerHost() {
        return this.delegate.getPeerHost();
    }

    @Override
    public final int getPeerPort() {
        return this.delegate.getPeerPort();
    }

    @Override
    public final Principal getPeerPrincipal() {
        return this.delegate.getPeerPrincipal();
    }

    @Override
    public final byte[] getPeerSignedCertificateTimestamp() {
        return this.delegate.getPeerSignedCertificateTimestamp();
    }

    @Override
    public final String[] getPeerSupportedSignatureAlgorithms() {
        return (String[]) PEER_SUPPORTED_SIGNATURE_ALGORITHMS.clone();
    }

    @Override
    public final String getProtocol() {
        return this.delegate.getProtocol();
    }

    @Override
    public final String getRequestedServerName() {
        return this.delegate.getRequestedServerName();
    }

    @Override
    public final SSLSessionContext getSessionContext() {
        return this.delegate.getSessionContext();
    }

    @Override
    public final List<byte[]> getStatusResponses() {
        return this.delegate.getStatusResponses();
    }

    @Override
    public final Object getValue(String str) {
        return this.delegate.getValue(str);
    }

    @Override
    public final String[] getValueNames() {
        return this.delegate.getValueNames();
    }

    @Override
    public final void invalidate() {
        this.delegate.invalidate();
    }

    @Override
    public final boolean isValid() {
        return this.delegate.isValid();
    }

    @Override
    public final void putValue(String str, Object obj) {
        this.delegate.putValue(this, str, obj);
    }

    @Override
    public final void removeValue(String str) {
        this.delegate.removeValue(this, str);
    }
}
