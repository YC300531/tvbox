package org.conscrypt;

import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
final class SSLNullSession implements ConscryptSession, Cloneable {
    public static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
    private long creationTime;
    private long lastAccessedTime;

    public static class fun1 {
    }

    public static class DefaultHolder {
        public static final SSLNullSession NULL_SESSION = new SSLNullSession(null);

        private DefaultHolder() {
        }
    }

    private SSLNullSession() {
        long currentTimeMillis = System.currentTimeMillis();
        this.creationTime = currentTimeMillis;
        this.lastAccessedTime = currentTimeMillis;
    }

    public SSLNullSession(1 r1) {
        this();
    }

    public static ConscryptSession getNullSession() {
        return DefaultHolder.NULL_SESSION;
    }

    @Override
    public int getApplicationBufferSize() {
        return 16384;
    }

    @Override
    public String getApplicationProtocol() {
        return null;
    }

    @Override
    public String getCipherSuite() {
        return INVALID_CIPHER;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public byte[] getId() {
        return EmptyArray.BYTE;
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
        throw new SSLPeerUnverifiedException("No peer certificate");
    }

    @Override
    public java.security.cert.X509Certificate[] getPeerCertificates() {
        throw new SSLPeerUnverifiedException("No peer certificate");
    }

    @Override
    public String getPeerHost() {
        return null;
    }

    @Override
    public int getPeerPort() {
        return -1;
    }

    @Override
    public Principal getPeerPrincipal() {
        throw new SSLPeerUnverifiedException("No peer certificate");
    }

    @Override
    public byte[] getPeerSignedCertificateTimestamp() {
        return EmptyArray.BYTE;
    }

    @Override
    public String getProtocol() {
        return "NONE";
    }

    @Override
    public String getRequestedServerName() {
        return null;
    }

    @Override
    public SSLSessionContext getSessionContext() {
        return null;
    }

    @Override
    public List<byte[]> getStatusResponses() {
        return Collections.emptyList();
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
