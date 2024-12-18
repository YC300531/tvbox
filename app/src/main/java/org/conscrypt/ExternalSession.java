package org.conscrypt;

import java.security.Principal;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
final class ExternalSession implements ConscryptSession {
    private final Provider provider;
    private final HashMap<String, Object> values = new HashMap<>(2);

    public interface Provider {
        ConscryptSession provideSession();
    }

    public ExternalSession(Provider provider) {
        this.provider = provider;
    }

    @Override
    public int getApplicationBufferSize() {
        return this.provider.provideSession().getApplicationBufferSize();
    }

    @Override
    public String getApplicationProtocol() {
        return this.provider.provideSession().getApplicationProtocol();
    }

    @Override
    public String getCipherSuite() {
        return this.provider.provideSession().getCipherSuite();
    }

    @Override
    public long getCreationTime() {
        return this.provider.provideSession().getCreationTime();
    }

    @Override
    public byte[] getId() {
        return this.provider.provideSession().getId();
    }

    @Override
    public long getLastAccessedTime() {
        return this.provider.provideSession().getLastAccessedTime();
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return this.provider.provideSession().getLocalCertificates();
    }

    @Override
    public Principal getLocalPrincipal() {
        return this.provider.provideSession().getLocalPrincipal();
    }

    @Override
    public int getPacketBufferSize() {
        return this.provider.provideSession().getPacketBufferSize();
    }

    @Override
    public X509Certificate[] getPeerCertificateChain() {
        return this.provider.provideSession().getPeerCertificateChain();
    }

    @Override
    public java.security.cert.X509Certificate[] getPeerCertificates() {
        return this.provider.provideSession().getPeerCertificates();
    }

    @Override
    public String getPeerHost() {
        return this.provider.provideSession().getPeerHost();
    }

    @Override
    public int getPeerPort() {
        return this.provider.provideSession().getPeerPort();
    }

    @Override
    public Principal getPeerPrincipal() {
        return this.provider.provideSession().getPeerPrincipal();
    }

    @Override
    public byte[] getPeerSignedCertificateTimestamp() {
        return this.provider.provideSession().getPeerSignedCertificateTimestamp();
    }

    @Override
    public String getProtocol() {
        return this.provider.provideSession().getProtocol();
    }

    @Override
    public String getRequestedServerName() {
        return this.provider.provideSession().getRequestedServerName();
    }

    @Override
    public SSLSessionContext getSessionContext() {
        return this.provider.provideSession().getSessionContext();
    }

    @Override
    public List<byte[]> getStatusResponses() {
        return this.provider.provideSession().getStatusResponses();
    }

    @Override
    public Object getValue(String str) {
        if (str != null) {
            return this.values.get(str);
        }
        throw new IllegalArgumentException("name == null");
    }

    @Override
    public String[] getValueNames() {
        return (String[]) this.values.keySet().toArray(new String[this.values.size()]);
    }

    @Override
    public void invalidate() {
        this.provider.provideSession().invalidate();
    }

    @Override
    public boolean isValid() {
        return this.provider.provideSession().isValid();
    }

    @Override
    public void putValue(String str, Object obj) {
        putValue(this, str, obj);
    }

    public void putValue(SSLSession sSLSession, String str, Object obj) {
        if (str == null || obj == null) {
            throw new IllegalArgumentException("name == null || value == null");
        }
        Object put = this.values.put(str, obj);
        if (obj instanceof SSLSessionBindingListener) {
            ((SSLSessionBindingListener) obj).valueBound(new SSLSessionBindingEvent(sSLSession, str));
        }
        if (put instanceof SSLSessionBindingListener) {
            ((SSLSessionBindingListener) put).valueUnbound(new SSLSessionBindingEvent(sSLSession, str));
        }
    }

    @Override
    public void removeValue(String str) {
        removeValue(this, str);
    }

    public void removeValue(SSLSession sSLSession, String str) {
        if (str == null) {
            throw new IllegalArgumentException("name == null");
        }
        Object remove = this.values.remove(str);
        if (remove instanceof SSLSessionBindingListener) {
            ((SSLSessionBindingListener) remove).valueUnbound(new SSLSessionBindingEvent(sSLSession, str));
        }
    }
}
