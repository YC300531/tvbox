package org.conscrypt;

import androidx.base.a.a;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import org.conscrypt.NativeRef;
import org.conscrypt.SSLUtils;
public abstract class NativeSslSession {
    private static final Logger logger = Logger.getLogger(NativeSslSession.class.getName());

    public static class fun1 {
    }

    public static final class Impl extends NativeSslSession {
        private final String cipherSuite;
        private final AbstractSessionContext context;
        private final String host;
        private final X509Certificate[] peerCertificates;
        private final byte[] peerOcspStapledResponse;
        private final byte[] peerSignedCertificateTimestamp;
        private final int port;
        private final String protocol;
        private final NativeRef.SSL_SESSION ref;

        private Impl(AbstractSessionContext abstractSessionContext, NativeRef.SSL_SESSION ssl_session, String str, int i, X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2) {
            this.context = abstractSessionContext;
            this.host = str;
            this.port = i;
            this.peerCertificates = x509CertificateArr;
            this.peerOcspStapledResponse = bArr;
            this.peerSignedCertificateTimestamp = bArr2;
            this.protocol = NativeCrypto.SSL_SESSION_get_version(ssl_session.address);
            this.cipherSuite = NativeCrypto.cipherSuiteToJava(NativeCrypto.SSL_SESSION_cipher(ssl_session.address));
            this.ref = ssl_session;
        }

        public Impl(AbstractSessionContext abstractSessionContext, NativeRef.SSL_SESSION ssl_session, String str, int i, X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2, 1 r8) {
            this(abstractSessionContext, ssl_session, str, i, x509CertificateArr, bArr, bArr2);
        }

        public long getCreationTime() {
            return NativeCrypto.SSL_SESSION_get_time(this.ref.address);
        }

        @Override
        public String getCipherSuite() {
            return this.cipherSuite;
        }

        @Override
        public byte[] getId() {
            return NativeCrypto.SSL_SESSION_session_id(this.ref.address);
        }

        @Override
        public String getPeerHost() {
            return this.host;
        }

        @Override
        public byte[] getPeerOcspStapledResponse() {
            return this.peerOcspStapledResponse;
        }

        @Override
        public int getPeerPort() {
            return this.port;
        }

        @Override
        public byte[] getPeerSignedCertificateTimestamp() {
            return this.peerSignedCertificateTimestamp;
        }

        @Override
        public String getProtocol() {
            return this.protocol;
        }

        @Override
        public boolean isSingleUse() {
            return NativeCrypto.SSL_SESSION_should_be_single_use(this.ref.address);
        }

        @Override
        public boolean isValid() {
            return System.currentTimeMillis() - (Math.max(0L, Math.min((long) this.context.getSessionTimeout(), NativeCrypto.SSL_SESSION_get_timeout(this.ref.address))) * 1000) < getCreationTime();
        }

        @Override
        public void offerToResume(NativeSsl nativeSsl) {
            nativeSsl.offerToResumeSession(this.ref.address);
        }

        @Override
        public byte[] toBytes() {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeInt(SSLUtils.SessionType.OPEN_SSL_WITH_TLS_SCT.value);
                byte[] i2d_SSL_SESSION = NativeCrypto.i2d_SSL_SESSION(this.ref.address);
                dataOutputStream.writeInt(i2d_SSL_SESSION.length);
                dataOutputStream.write(i2d_SSL_SESSION);
                dataOutputStream.writeInt(this.peerCertificates.length);
                for (X509Certificate x509Certificate : this.peerCertificates) {
                    byte[] encoded = x509Certificate.getEncoded();
                    dataOutputStream.writeInt(encoded.length);
                    dataOutputStream.write(encoded);
                }
                if (this.peerOcspStapledResponse != null) {
                    dataOutputStream.writeInt(1);
                    dataOutputStream.writeInt(this.peerOcspStapledResponse.length);
                    dataOutputStream.write(this.peerOcspStapledResponse);
                } else {
                    dataOutputStream.writeInt(0);
                }
                byte[] bArr = this.peerSignedCertificateTimestamp;
                if (bArr != null) {
                    dataOutputStream.writeInt(bArr.length);
                    dataOutputStream.write(this.peerSignedCertificateTimestamp);
                } else {
                    dataOutputStream.writeInt(0);
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                NativeSslSession.logger.log(Level.WARNING, "Failed to convert saved SSL Session: ", (Throwable) e);
                return null;
            } catch (CertificateEncodingException e2) {
                NativeSslSession.log(e2);
                return null;
            }
        }

        @Override
        public SSLSession toSSLSession() {
            return new SSLSession() {
                @Override
                public int getApplicationBufferSize() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String getCipherSuite() {
                    return Impl.this.getCipherSuite();
                }

                @Override
                public long getCreationTime() {
                    return Impl.this.getCreationTime();
                }

                @Override
                public byte[] getId() {
                    return Impl.this.getId();
                }

                @Override
                public long getLastAccessedTime() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Certificate[] getLocalCertificates() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Principal getLocalPrincipal() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int getPacketBufferSize() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public javax.security.cert.X509Certificate[] getPeerCertificateChain() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Certificate[] getPeerCertificates() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String getPeerHost() {
                    return Impl.this.getPeerHost();
                }

                @Override
                public int getPeerPort() {
                    return Impl.this.getPeerPort();
                }

                @Override
                public Principal getPeerPrincipal() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String getProtocol() {
                    return Impl.this.getProtocol();
                }

                @Override
                public SSLSessionContext getSessionContext() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Object getValue(String str) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String[] getValueNames() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void invalidate() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public boolean isValid() {
                    return Impl.this.isValid();
                }

                @Override
                public void putValue(String str, Object obj) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void removeValue(String str) {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    private static void checkRemaining(ByteBuffer byteBuffer, int i) {
        if (i < 0) {
            throw new IOException(a.h("Length is negative: ", i));
        }
        if (i <= byteBuffer.remaining()) {
            return;
        }
        StringBuilder n = a.n("Length of blob is longer than available: ", i, " > ");
        n.append(byteBuffer.remaining());
        throw new IOException(n.toString());
    }

    private static byte[] getOcspResponse(ConscryptSession conscryptSession) {
        List<byte[]> statusResponses = conscryptSession.getStatusResponses();
        if (statusResponses.size() >= 1) {
            return statusResponses.get(0);
        }
        return null;
    }

    public static void log(Throwable th) {
        logger.log(Level.INFO, "Error inflating SSL session: {0}", th.getMessage() != null ? th.getMessage() : th.getClass().getName());
    }

    public static org.conscrypt.NativeSslSession newInstance(org.conscrypt.AbstractSessionContext r14, byte[] r15, java.lang.String r16, int r17) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: org.conscrypt.NativeSslSession.newInstance(org.conscrypt.AbstractSessionContext, byte[], java.lang.String, int):org.conscrypt.NativeSslSession");
    }

    public static NativeSslSession newInstance(NativeRef.SSL_SESSION ssl_session, ConscryptSession conscryptSession) {
        AbstractSessionContext abstractSessionContext = (AbstractSessionContext) conscryptSession.getSessionContext();
        return abstractSessionContext instanceof ClientSessionContext ? new Impl(abstractSessionContext, ssl_session, conscryptSession.getPeerHost(), conscryptSession.getPeerPort(), conscryptSession.getPeerCertificates(), getOcspResponse(conscryptSession), conscryptSession.getPeerSignedCertificateTimestamp(), null) : new Impl(abstractSessionContext, ssl_session, null, -1, null, null, null, null);
    }

    public abstract String getCipherSuite();

    public abstract byte[] getId();

    public abstract String getPeerHost();

    public abstract byte[] getPeerOcspStapledResponse();

    public abstract int getPeerPort();

    public abstract byte[] getPeerSignedCertificateTimestamp();

    public abstract String getProtocol();

    public abstract boolean isSingleUse();

    public abstract boolean isValid();

    public abstract void offerToResume(NativeSsl nativeSsl);

    public abstract byte[] toBytes();

    public abstract SSLSession toSSLSession();
}
