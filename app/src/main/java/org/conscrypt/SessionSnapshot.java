package org.conscrypt;

import java.security.Principal;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
final class SessionSnapshot implements ConscryptSession {
    private final String applicationProtocol;
    private final String cipherSuite;
    private final long creationTime;
    private final byte[] id;
    private final long lastAccessedTime;
    private final String peerHost;
    private final int peerPort;
    private final byte[] peerTlsSctData;
    private final String protocol;
    private final String requestedServerName;
    private final SSLSessionContext sessionContext;
    private final List<byte[]> statusResponses;

    public SessionSnapshot(ConscryptSession conscryptSession) {
        this.sessionContext = conscryptSession.getSessionContext();
        this.id = conscryptSession.getId();
        this.requestedServerName = conscryptSession.getRequestedServerName();
        this.statusResponses = conscryptSession.getStatusResponses();
        this.peerTlsSctData = conscryptSession.getPeerSignedCertificateTimestamp();
        this.creationTime = conscryptSession.getCreationTime();
        this.lastAccessedTime = conscryptSession.getLastAccessedTime();
        this.cipherSuite = conscryptSession.getCipherSuite();
        this.protocol = conscryptSession.getProtocol();
        this.peerHost = conscryptSession.getPeerHost();
        this.peerPort = conscryptSession.getPeerPort();
        this.applicationProtocol = conscryptSession.getApplicationProtocol();
    }

    @Override
    public int getApplicationBufferSize() {
        return 16384;
    }

    @Override
    public String getApplicationProtocol() {
        return this.applicationProtocol;
    }

    @Override
    public String getCipherSuite() {
        return this.cipherSuite;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public byte[] getId() {
        return this.id;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return null;
    }

    @Override
    public Principal getLocalPrincipal() {
        return null;
    }

    @Override
    public int getPacketBufferSize() {
        return NativeConstants.SSL3_RT_MAX_PACKET_SIZE;
    }

    @Override
    public X509Certificate[] getPeerCertificateChain() {
        throw new SSLPeerUnverifiedException("No peer certificates");
    }

    @Override
    public java.security.cert.X509Certificate[] getPeerCertificates() {
        throw new SSLPeerUnverifiedException("No peer certificates");
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
        throw new SSLPeerUnverifiedException("No peer certificates");
    }

    @Override
    public byte[] getPeerSignedCertificateTimestamp() {
        byte[] bArr = this.peerTlsSctData;
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getRequestedServerName() {
        return this.requestedServerName;
    }

    @Override
    public SSLSessionContext getSessionContext() {
        return this.sessionContext;
    }

    @Override
    public List<byte[]> getStatusResponses() {
        ArrayList arrayList = new ArrayList(this.statusResponses.size());
        for (byte[] bArr : this.statusResponses) {
            arrayList.add(bArr.clone());
        }
        return arrayList;
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
    }

    @Override
    public boolean isValid() {
        return false;
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
}
