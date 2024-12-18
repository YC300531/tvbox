package org.conscrypt;

import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSessionContext;
public final class ActiveSession implements ConscryptSession {
    private String applicationProtocol;
    private long creationTime;
    private byte[] id;
    private X509Certificate[] localCertificates;
    private volatile javax.security.cert.X509Certificate[] peerCertificateChain;
    private byte[] peerCertificateOcspData;
    private X509Certificate[] peerCertificates;
    private String peerHost;
    private byte[] peerTlsSctData;
    private String protocol;
    private AbstractSessionContext sessionContext;
    private final NativeSsl ssl;
    private int peerPort = -1;
    private long lastAccessedTime = 0;

    public ActiveSession(NativeSsl nativeSsl, AbstractSessionContext abstractSessionContext) {
        this.ssl = (NativeSsl) Preconditions.checkNotNull(nativeSsl, "ssl");
        this.sessionContext = (AbstractSessionContext) Preconditions.checkNotNull(abstractSessionContext, "sessionContext");
    }

    private void checkPeerCertificatesPresent() {
        X509Certificate[] x509CertificateArr = this.peerCertificates;
        if (x509CertificateArr == null || x509CertificateArr.length == 0) {
            throw new SSLPeerUnverifiedException("No peer certificates");
        }
    }

    private void configurePeer(String str, int i, X509Certificate[] x509CertificateArr) {
        this.peerHost = str;
        this.peerPort = i;
        this.peerCertificates = x509CertificateArr;
        synchronized (this.ssl) {
            this.peerCertificateOcspData = this.ssl.getPeerCertificateOcspData();
            this.peerTlsSctData = this.ssl.getPeerTlsSctData();
        }
    }

    @Override
    public int getApplicationBufferSize() {
        return 16384;
    }

    @Override
    public String getApplicationProtocol() {
        String str = this.applicationProtocol;
        if (str == null) {
            synchronized (this.ssl) {
                str = SSLUtils.toProtocolString(this.ssl.getApplicationProtocol());
            }
            this.applicationProtocol = str;
        }
        return str;
    }

    @Override
    public String getCipherSuite() {
        String cipherSuite;
        synchronized (this.ssl) {
            cipherSuite = this.ssl.getCipherSuite();
        }
        return cipherSuite == null ? SSLNullSession.INVALID_CIPHER : cipherSuite;
    }

    @Override
    public long getCreationTime() {
        if (this.creationTime == 0) {
            synchronized (this.ssl) {
                this.creationTime = this.ssl.getTime();
            }
        }
        return this.creationTime;
    }

    @Override
    public byte[] getId() {
        if (this.id == null) {
            synchronized (this.ssl) {
                this.id = this.ssl.getSessionId();
            }
        }
        byte[] bArr = this.id;
        return bArr != null ? (byte[]) bArr.clone() : EmptyArray.BYTE;
    }

    @Override
    public long getLastAccessedTime() {
        long j = this.lastAccessedTime;
        return j == 0 ? getCreationTime() : j;
    }

    @Override
    public Certificate[] getLocalCertificates() {
        if (this.localCertificates == null) {
            synchronized (this.ssl) {
                this.localCertificates = this.ssl.getLocalCertificates();
            }
        }
        X509Certificate[] x509CertificateArr = this.localCertificates;
        if (x509CertificateArr == null) {
            return null;
        }
        return (X509Certificate[]) x509CertificateArr.clone();
    }

    @Override
    public Principal getLocalPrincipal() {
        X509Certificate[] x509CertificateArr = (X509Certificate[]) getLocalCertificates();
        if (x509CertificateArr == null || x509CertificateArr.length <= 0) {
            return null;
        }
        return x509CertificateArr[0].getSubjectX500Principal();
    }

    @Override
    public int getPacketBufferSize() {
        return NativeConstants.SSL3_RT_MAX_PACKET_SIZE;
    }

    @Override
    public javax.security.cert.X509Certificate[] getPeerCertificateChain() {
        checkPeerCertificatesPresent();
        javax.security.cert.X509Certificate[] x509CertificateArr = this.peerCertificateChain;
        if (x509CertificateArr == null) {
            javax.security.cert.X509Certificate[] certificateChain = SSLUtils.toCertificateChain(this.peerCertificates);
            this.peerCertificateChain = certificateChain;
            return certificateChain;
        }
        return x509CertificateArr;
    }

    @Override
    public X509Certificate[] getPeerCertificates() {
        checkPeerCertificatesPresent();
        return (X509Certificate[]) this.peerCertificates.clone();
    }

    @Override
    public String getPeerHost() {
        return this.peerHost;
    }

    @Override
    public int getPeerPort() {
        return this.peerPort;
    }

    @Override
    public Principal getPeerPrincipal() {
        checkPeerCertificatesPresent();
        return this.peerCertificates[0].getSubjectX500Principal();
    }

    @Override
    public byte[] getPeerSignedCertificateTimestamp() {
        byte[] bArr = this.peerTlsSctData;
        if (bArr == null) {
            return null;
        }
        return (byte[]) bArr.clone();
    }

    @Override
    public String getProtocol() {
        String str = this.protocol;
        if (str == null) {
            synchronized (this.ssl) {
                str = this.ssl.getVersion();
            }
            this.protocol = str;
        }
        return str;
    }

    @Override
    public String getRequestedServerName() {
        String requestedServerName;
        synchronized (this.ssl) {
            requestedServerName = this.ssl.getRequestedServerName();
        }
        return requestedServerName;
    }

    @Override
    public SSLSessionContext getSessionContext() {
        if (isValid()) {
            return this.sessionContext;
        }
        return null;
    }

    @Override
    public List<byte[]> getStatusResponses() {
        byte[] bArr = this.peerCertificateOcspData;
        return bArr == null ? Collections.emptyList() : Collections.singletonList(bArr.clone());
    }

    @Override
    public Object getValue(String str) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public String[] getValueNames() {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public void invalidate() {
        synchronized (this.ssl) {
            this.ssl.setTimeout(0L);
        }
    }

    @Override
    public boolean isValid() {
        boolean z;
        synchronized (this.ssl) {
            z = System.currentTimeMillis() - this.ssl.getTimeout() < this.ssl.getTime();
        }
        return z;
    }

    public void onPeerCertificateAvailable(String str, int i) {
        synchronized (this.ssl) {
            this.id = null;
            if (this.localCertificates == null) {
                this.localCertificates = this.ssl.getLocalCertificates();
            }
            if (this.peerCertificates == null) {
                configurePeer(str, i, this.ssl.getPeerCertificates());
            }
        }
    }

    public void onPeerCertificatesReceived(String str, int i, X509Certificate[] x509CertificateArr) {
        configurePeer(str, i, x509CertificateArr);
    }

    @Override
    public void putValue(String str, Object obj) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public void removeValue(String str) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
All calls to this method should be intercepted by ExternalSession.");
    }

    public void setLastAccessedTime(long j) {
        this.lastAccessedTime = j;
    }
}
