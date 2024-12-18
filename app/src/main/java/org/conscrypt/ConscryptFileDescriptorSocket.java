package org.conscrypt;

import androidx.base.a.b;
import androidx.base.i4.a;
import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.ExternalSession;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.SSLParametersImpl;
public class ConscryptFileDescriptorSocket extends OpenSSLSocketImpl implements NativeCrypto.SSLHandshakeCallbacks, SSLParametersImpl.PSKCallbacks, SSLParametersImpl.AliasChooser {
    private static final boolean DBG_STATE = false;
    private final ActiveSession activeSession;
    private OpenSSLKey channelIdPrivateKey;
    private SessionSnapshot closedSession;
    private final SSLSession externalSession;
    private final Object guard;
    private int handshakeTimeoutMilliseconds;
    private SSLInputStream is;
    private SSLOutputStream os;
    private final NativeSsl ssl;
    private final SSLParametersImpl sslParameters;
    private int state;
    private int writeTimeoutMilliseconds;

    public class SSLInputStream extends InputStream {
        private final Object readLock = new Object();

        public SSLInputStream() {
        }

        @Override
        public int available() {
            return ssl.getPendingReadableBytes();
        }

        public void awaitPendingOps() {
            synchronized (this.readLock) {
            }
        }

        @Override
        public int read() {
            byte[] bArr = new byte[1];
            if (read(bArr, 0, 1) != -1) {
                return bArr[0] & ExifInterface.MARKER;
            }
            return -1;
        }

        @Override
        public int read(byte[] bArr, int i, int i2) {
            int read;
            Platform.blockGuardOnNetwork();
            checkOpen();
            ArrayUtils.checkOffsetAndCount(bArr.length, i, i2);
            if (i2 == 0) {
                return 0;
            }
            synchronized (this.readLock) {
                synchronized (ssl) {
                    if (state == 8) {
                        throw new SocketException("socket is closed");
                    }
                }
                read = ssl.read(Platform.getFileDescriptor(socket), bArr, i, i2, getSoTimeout());
                if (read == -1) {
                    synchronized (ssl) {
                        if (state == 8) {
                            throw new SocketException("socket is closed");
                        }
                    }
                }
            }
            return read;
        }
    }

    public class SSLOutputStream extends OutputStream {
        private final Object writeLock = new Object();

        public SSLOutputStream() {
        }

        public void awaitPendingOps() {
            synchronized (this.writeLock) {
            }
        }

        @Override
        public void write(int i) {
            write(new byte[]{(byte) (i & 255)});
        }

        @Override
        public void write(byte[] bArr, int i, int i2) {
            Platform.blockGuardOnNetwork();
            checkOpen();
            ArrayUtils.checkOffsetAndCount(bArr.length, i, i2);
            if (i2 == 0) {
                return;
            }
            synchronized (this.writeLock) {
                synchronized (ssl) {
                    if (state == 8) {
                        throw new SocketException("socket is closed");
                    }
                }
                ssl.write(Platform.getFileDescriptor(socket), bArr, i, i2, writeTimeoutMilliseconds);
                synchronized (ssl) {
                    if (state == 8) {
                        throw new SocketException("socket is closed");
                    }
                }
            }
        }
    }

    public ConscryptFileDescriptorSocket(String str, int i, InetAddress inetAddress, int i2, SSLParametersImpl sSLParametersImpl) {
        super(str, i, inetAddress, i2);
        this.state = 0;
        this.guard = Platform.closeGuardGet();
        this.externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() {
            @Override
            public ConscryptSession provideSession() {
                return provideSession();
            }
        }));
        this.writeTimeoutMilliseconds = 0;
        this.handshakeTimeoutMilliseconds = -1;
        this.sslParameters = sSLParametersImpl;
        NativeSsl newSsl = newSsl(sSLParametersImpl, this);
        this.ssl = newSsl;
        this.activeSession = new ActiveSession(newSsl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(String str, int i, SSLParametersImpl sSLParametersImpl) {
        super(str, i);
        this.state = 0;
        this.guard = Platform.closeGuardGet();
        this.externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() {
            @Override
            public ConscryptSession provideSession() {
                return provideSession();
            }
        }));
        this.writeTimeoutMilliseconds = 0;
        this.handshakeTimeoutMilliseconds = -1;
        this.sslParameters = sSLParametersImpl;
        NativeSsl newSsl = newSsl(sSLParametersImpl, this);
        this.ssl = newSsl;
        this.activeSession = new ActiveSession(newSsl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2, SSLParametersImpl sSLParametersImpl) {
        super(inetAddress, i, inetAddress2, i2);
        this.state = 0;
        this.guard = Platform.closeGuardGet();
        this.externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() {
            @Override
            public ConscryptSession provideSession() {
                return provideSession();
            }
        }));
        this.writeTimeoutMilliseconds = 0;
        this.handshakeTimeoutMilliseconds = -1;
        this.sslParameters = sSLParametersImpl;
        NativeSsl newSsl = newSsl(sSLParametersImpl, this);
        this.ssl = newSsl;
        this.activeSession = new ActiveSession(newSsl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(InetAddress inetAddress, int i, SSLParametersImpl sSLParametersImpl) {
        super(inetAddress, i);
        this.state = 0;
        this.guard = Platform.closeGuardGet();
        this.externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() {
            @Override
            public ConscryptSession provideSession() {
                return provideSession();
            }
        }));
        this.writeTimeoutMilliseconds = 0;
        this.handshakeTimeoutMilliseconds = -1;
        this.sslParameters = sSLParametersImpl;
        NativeSsl newSsl = newSsl(sSLParametersImpl, this);
        this.ssl = newSsl;
        this.activeSession = new ActiveSession(newSsl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(Socket socket, String str, int i, boolean z, SSLParametersImpl sSLParametersImpl) {
        super(socket, str, i, z);
        this.state = 0;
        this.guard = Platform.closeGuardGet();
        this.externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() {
            @Override
            public ConscryptSession provideSession() {
                return provideSession();
            }
        }));
        this.writeTimeoutMilliseconds = 0;
        this.handshakeTimeoutMilliseconds = -1;
        this.sslParameters = sSLParametersImpl;
        NativeSsl newSsl = newSsl(sSLParametersImpl, this);
        this.ssl = newSsl;
        this.activeSession = new ActiveSession(newSsl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(SSLParametersImpl sSLParametersImpl) {
        this.state = 0;
        this.guard = Platform.closeGuardGet();
        this.externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() {
            @Override
            public ConscryptSession provideSession() {
                return provideSession();
            }
        }));
        this.writeTimeoutMilliseconds = 0;
        this.handshakeTimeoutMilliseconds = -1;
        this.sslParameters = sSLParametersImpl;
        NativeSsl newSsl = newSsl(sSLParametersImpl, this);
        this.ssl = newSsl;
        this.activeSession = new ActiveSession(newSsl, sSLParametersImpl.getSessionContext());
    }

    private void assertReadableOrWriteableState() {
        int i = this.state;
        if (i == 5 || i == 4) {
            return;
        }
        StringBuilder d = b.d("Invalid state: ");
        d.append(this.state);
        throw new AssertionError(d.toString());
    }

    private ClientSessionContext clientSessionContext() {
        return this.sslParameters.getClientSessionContext();
    }

    private void closeUnderlyingSocket() {
        super.close();
    }

    private void free() {
        if (this.ssl.isClosed()) {
            return;
        }
        this.ssl.close();
        Platform.closeGuardClose(this.guard);
    }

    private static NativeSsl newSsl(SSLParametersImpl sSLParametersImpl, ConscryptFileDescriptorSocket conscryptFileDescriptorSocket) {
        return NativeSsl.newInstance(sSLParametersImpl, conscryptFileDescriptorSocket, conscryptFileDescriptorSocket, conscryptFileDescriptorSocket);
    }

    private ConscryptSession provideAfterHandshakeSession() {
        return this.state < 2 ? SSLNullSession.getNullSession() : provideSession();
    }

    public ConscryptSession provideHandshakeSession() {
        ConscryptSession nullSession;
        synchronized (this.ssl) {
            int i = this.state;
            nullSession = (i < 2 || i >= 5) ? SSLNullSession.getNullSession() : this.activeSession;
        }
        return nullSession;
    }

    public org.conscrypt.ConscryptSession provideSession() {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: org.conscrypt.ConscryptFileDescriptorSocket.provideSession():org.conscrypt.ConscryptSession");
    }

    private AbstractSessionContext sessionContext() {
        return this.sslParameters.getSessionContext();
    }

    private void shutdownAndFreeSslNative() {
        try {
            Platform.blockGuardOnNetwork();
            this.ssl.shutdown(Platform.getFileDescriptor(this.socket));
        } catch (IOException unused) {
        } catch (Throwable th) {
            free();
            closeUnderlyingSocket();
            throw th;
        }
        free();
        closeUnderlyingSocket();
    }

    private void transitionTo(int i) {
        int i2;
        if (i == 8 && !this.ssl.isClosed() && (i2 = this.state) >= 2 && i2 < 8) {
            this.closedSession = new SessionSnapshot(this.activeSession);
        }
        this.state = i;
    }

    private void waitForHandshake() {
        startHandshake();
        synchronized (this.ssl) {
            while (true) {
                int i = this.state;
                if (i == 5 || i == 4 || i == 8) {
                    break;
                }
                try {
                    this.ssl.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted waiting for handshake", e);
                }
            }
            throw new SocketException("Socket is closed");
        }
    }

    @Override
    public final String chooseClientAlias(X509KeyManager x509KeyManager, X500Principal[] x500PrincipalArr, String[] strArr) {
        return x509KeyManager.chooseClientAlias(strArr, x500PrincipalArr, this);
    }

    @Override
    public final String chooseClientPSKIdentity(PSKKeyManager pSKKeyManager, String str) {
        return pSKKeyManager.chooseClientKeyIdentity(str, this);
    }

    @Override
    public final String chooseServerAlias(X509KeyManager x509KeyManager, String str) {
        return x509KeyManager.chooseServerAlias(str, null, this);
    }

    @Override
    public final String chooseServerPSKIdentityHint(PSKKeyManager pSKKeyManager) {
        return pSKKeyManager.chooseServerKeyIdentityHint(this);
    }

    @Override
    public final void clientCertificateRequested(byte[] bArr, int[] iArr, byte[][] bArr2) {
        this.ssl.chooseClientCertificate(bArr, iArr, bArr2);
    }

    @Override
    public final int clientPSKKeyRequested(String str, byte[] bArr, byte[] bArr2) {
        return this.ssl.clientPSKKeyRequested(str, bArr, bArr2);
    }

    @Override
    public final void close() {
        NativeSsl nativeSsl = this.ssl;
        if (nativeSsl == null) {
            return;
        }
        synchronized (nativeSsl) {
            int i = this.state;
            if (i == 8) {
                return;
            }
            transitionTo(8);
            if (i == 0) {
                free();
                closeUnderlyingSocket();
                this.ssl.notifyAll();
            } else if (i != 5 && i != 4) {
                this.ssl.interrupt();
                this.ssl.notifyAll();
            } else {
                this.ssl.notifyAll();
                SSLInputStream sSLInputStream = this.is;
                SSLOutputStream sSLOutputStream = this.os;
                if (sSLInputStream != null || sSLOutputStream != null) {
                    this.ssl.interrupt();
                }
                if (sSLInputStream != null) {
                    sSLInputStream.awaitPendingOps();
                }
                if (sSLOutputStream != null) {
                    sSLOutputStream.awaitPendingOps();
                }
                shutdownAndFreeSslNative();
            }
        }
    }

    @Override
    public byte[] exportKeyingMaterial(String str, byte[] bArr, int i) {
        synchronized (this.ssl) {
            int i2 = this.state;
            if (i2 >= 3 && i2 != 8) {
                return this.ssl.exportKeyingMaterial(str, bArr, i);
            }
            return null;
        }
    }

    public final void finalize() {
        try {
            Object obj = this.guard;
            if (obj != null) {
                Platform.closeGuardWarnIfOpen(obj);
            }
            NativeSsl nativeSsl = this.ssl;
            if (nativeSsl != null) {
                synchronized (nativeSsl) {
                    transitionTo(8);
                }
            }
        } finally {
            super.finalize();
        }
    }

    @Override
    public final SSLSession getActiveSession() {
        return this.activeSession;
    }

    @Override
    public final String getApplicationProtocol() {
        return provideAfterHandshakeSession().getApplicationProtocol();
    }

    @Override
    public final String[] getApplicationProtocols() {
        return this.sslParameters.getApplicationProtocols();
    }

    @Override
    public final byte[] getChannelId() {
        if (getUseClientMode()) {
            throw new IllegalStateException("Client mode");
        }
        synchronized (this.ssl) {
            if (this.state != 5) {
                throw new IllegalStateException("Channel ID is only available after handshake completes");
            }
        }
        return this.ssl.getTlsChannelId();
    }

    @Override
    public final boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override
    public final String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override
    public final String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    @Override
    public final String getHandshakeApplicationProtocol() {
        String applicationProtocol;
        synchronized (this.ssl) {
            int i = this.state;
            applicationProtocol = (i < 2 || i >= 5) ? null : getApplicationProtocol();
        }
        return applicationProtocol;
    }

    @Override
    public final SSLSession getHandshakeSession() {
        synchronized (this.ssl) {
            int i = this.state;
            if (i < 2 || i >= 5) {
                return null;
            }
            return Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() {
                @Override
                public ConscryptSession provideSession() {
                    return provideHandshakeSession();
                }
            }));
        }
    }

    @Override
    public final InputStream getInputStream() {
        SSLInputStream sSLInputStream;
        checkOpen();
        synchronized (this.ssl) {
            if (this.state == 8) {
                throw new SocketException("Socket is closed.");
            }
            if (this.is == null) {
                this.is = new SSLInputStream();
            }
            sSLInputStream = this.is;
        }
        waitForHandshake();
        return sSLInputStream;
    }

    @Override
    public final boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override
    public final OutputStream getOutputStream() {
        SSLOutputStream sSLOutputStream;
        checkOpen();
        synchronized (this.ssl) {
            if (this.state == 8) {
                throw new SocketException("Socket is closed.");
            }
            if (this.os == null) {
                this.os = new SSLOutputStream();
            }
            sSLOutputStream = this.os;
        }
        waitForHandshake();
        return sSLOutputStream;
    }

    @Override
    public final SecretKey getPSKKey(PSKKeyManager pSKKeyManager, String str, String str2) {
        return pSKKeyManager.getKey(str, str2, this);
    }

    @Override
    public final SSLParameters getSSLParameters() {
        SSLParameters sSLParameters = super.getSSLParameters();
        Platform.getSSLParameters(sSLParameters, this.sslParameters, this);
        return sSLParameters;
    }

    @Override
    public final SSLSession getSession() {
        return this.externalSession;
    }

    @Override
    public final int getSoWriteTimeout() {
        return this.writeTimeoutMilliseconds;
    }

    @Override
    public final String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override
    public final String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override
    public byte[] getTlsUnique() {
        return this.ssl.getTlsUnique();
    }

    @Override
    public final boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override
    public final boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    @Override
    public final void onNewSessionEstablished(long j) {
        try {
            NativeCrypto.SSL_SESSION_up_ref(j);
            sessionContext().cacheSession(NativeSslSession.newInstance(new NativeRef.SSL_SESSION(j), this.activeSession));
        } catch (Exception unused) {
        }
    }

    @Override
    public final void onSSLStateChange(int i, int i2) {
        if (i != 32) {
            return;
        }
        synchronized (this.ssl) {
            if (this.state == 8) {
                return;
            }
            transitionTo(5);
            notifyHandshakeCompletedListeners();
            synchronized (this.ssl) {
                this.ssl.notifyAll();
            }
        }
    }

    @Override
    public int selectApplicationProtocol(byte[] bArr) {
        ApplicationProtocolSelectorAdapter applicationProtocolSelector = this.sslParameters.getApplicationProtocolSelector();
        if (applicationProtocolSelector == null) {
            return 3;
        }
        return applicationProtocolSelector.selectApplicationProtocol(bArr);
    }

    @Override
    public final void serverCertificateRequested() {
        synchronized (this.ssl) {
            this.ssl.configureServerCertificate();
        }
    }

    @Override
    public final int serverPSKKeyRequested(String str, String str2, byte[] bArr) {
        return this.ssl.serverPSKKeyRequested(str, str2, bArr);
    }

    @Override
    public final long serverSessionRequested(byte[] bArr) {
        return 0L;
    }

    @Override
    public final void setApplicationProtocolSelector(ApplicationProtocolSelector applicationProtocolSelector) {
        setApplicationProtocolSelector(applicationProtocolSelector == null ? null : new ApplicationProtocolSelectorAdapter(this, applicationProtocolSelector));
    }

    @Override
    public final void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter) {
        this.sslParameters.setApplicationProtocolSelector(applicationProtocolSelectorAdapter);
    }

    @Override
    public final void setApplicationProtocols(String[] strArr) {
        this.sslParameters.setApplicationProtocols(strArr);
    }

    @Override
    public final void setChannelIdEnabled(boolean z) {
        if (getUseClientMode()) {
            throw new IllegalStateException("Client mode");
        }
        synchronized (this.ssl) {
            if (this.state != 0) {
                throw new IllegalStateException("Could not enable/disable Channel ID after the initial handshake has begun.");
            }
        }
        this.sslParameters.channelIdEnabled = z;
    }

    @Override
    public final void setChannelIdPrivateKey(PrivateKey privateKey) {
        if (!getUseClientMode()) {
            throw new IllegalStateException("Server mode");
        }
        synchronized (this.ssl) {
            if (this.state != 0) {
                throw new IllegalStateException("Could not change Channel ID private key after the initial handshake has begun.");
            }
        }
        if (privateKey == null) {
            this.sslParameters.channelIdEnabled = false;
            this.channelIdPrivateKey = null;
            return;
        }
        this.sslParameters.channelIdEnabled = true;
        try {
            ECParameterSpec params = privateKey instanceof ECKey ? ((ECKey) privateKey).getParams() : null;
            if (params == null) {
                params = OpenSSLECGroupContext.getCurveByName("prime256v1").getECParameterSpec();
            }
            this.channelIdPrivateKey = OpenSSLKey.fromECPrivateKeyForTLSStackOnly(privateKey, params);
        } catch (InvalidKeyException unused) {
        }
    }

    @Override
    public final void setEnableSessionCreation(boolean z) {
        this.sslParameters.setEnableSessionCreation(z);
    }

    @Override
    public final void setEnabledCipherSuites(String[] strArr) {
        this.sslParameters.setEnabledCipherSuites(strArr);
    }

    @Override
    public final void setEnabledProtocols(String[] strArr) {
        this.sslParameters.setEnabledProtocols(strArr);
    }

    @Override
    public final void setHandshakeTimeout(int i) {
        this.handshakeTimeoutMilliseconds = i;
    }

    @Override
    public final void setHostname(String str) {
        this.sslParameters.setUseSni(str != null);
        super.setHostname(str);
    }

    @Override
    public final void setNeedClientAuth(boolean z) {
        this.sslParameters.setNeedClientAuth(z);
    }

    @Override
    public final void setSSLParameters(SSLParameters sSLParameters) {
        super.setSSLParameters(sSLParameters);
        Platform.setSSLParameters(sSLParameters, this.sslParameters, this);
    }

    @Override
    public final void setSoWriteTimeout(int i) {
        this.writeTimeoutMilliseconds = i;
        Platform.setSocketWriteTimeout(this, i);
    }

    @Override
    public final void setUseClientMode(boolean z) {
        synchronized (this.ssl) {
            if (this.state != 0) {
                throw new IllegalArgumentException("Could not change the mode after the initial handshake has begun.");
            }
        }
        this.sslParameters.setUseClientMode(z);
    }

    @Override
    public final void setUseSessionTickets(boolean z) {
        this.sslParameters.setUseSessionTickets(z);
    }

    @Override
    public final void setWantClientAuth(boolean z) {
        this.sslParameters.setWantClientAuth(z);
    }

    @Override
    public final void startHandshake() {
        NativeSslSession cachedSession;
        checkOpen();
        synchronized (this.ssl) {
            if (this.state == 0) {
                transitionTo(2);
                boolean z = true;
                try {
                    try {
                        Platform.closeGuardOpen(this.guard, a.HEAD_VALUE_CONNECTION_CLOSE);
                        this.ssl.initialize(getHostname(), this.channelIdPrivateKey);
                        if (getUseClientMode() && (cachedSession = clientSessionContext().getCachedSession(getHostnameOrIP(), getPort(), this.sslParameters)) != null) {
                            cachedSession.offerToResume(this.ssl);
                        }
                        int soTimeout = getSoTimeout();
                        int soWriteTimeout = getSoWriteTimeout();
                        int i = this.handshakeTimeoutMilliseconds;
                        if (i >= 0) {
                            setSoTimeout(i);
                            setSoWriteTimeout(this.handshakeTimeoutMilliseconds);
                        }
                        synchronized (this.ssl) {
                            if (this.state == 8) {
                                synchronized (this.ssl) {
                                    transitionTo(8);
                                    this.ssl.notifyAll();
                                }
                                try {
                                    shutdownAndFreeSslNative();
                                    return;
                                } catch (IOException unused) {
                                    return;
                                }
                            }
                            try {
                                this.ssl.doHandshake(Platform.getFileDescriptor(this.socket), getSoTimeout());
                                this.activeSession.onPeerCertificateAvailable(getHostnameOrIP(), getPort());
                                synchronized (this.ssl) {
                                    if (this.state == 8) {
                                        synchronized (this.ssl) {
                                            transitionTo(8);
                                            this.ssl.notifyAll();
                                        }
                                        try {
                                            shutdownAndFreeSslNative();
                                            return;
                                        } catch (IOException unused2) {
                                            return;
                                        }
                                    }
                                    if (this.handshakeTimeoutMilliseconds >= 0) {
                                        setSoTimeout(soTimeout);
                                        setSoWriteTimeout(soWriteTimeout);
                                    }
                                    synchronized (this.ssl) {
                                        int i2 = this.state;
                                        if (i2 != 8) {
                                            z = false;
                                        }
                                        if (i2 == 2) {
                                            transitionTo(4);
                                        } else {
                                            transitionTo(5);
                                        }
                                        if (!z) {
                                            this.ssl.notifyAll();
                                        }
                                    }
                                    if (z) {
                                        synchronized (this.ssl) {
                                            transitionTo(8);
                                            this.ssl.notifyAll();
                                        }
                                        try {
                                            shutdownAndFreeSslNative();
                                        } catch (IOException unused3) {
                                        }
                                    }
                                }
                            } catch (CertificateException e) {
                                SSLHandshakeException sSLHandshakeException = new SSLHandshakeException(e.getMessage());
                                sSLHandshakeException.initCause(e);
                                throw sSLHandshakeException;
                            } catch (SSLException e2) {
                                synchronized (this.ssl) {
                                    if (this.state != 8) {
                                        if (e2.getMessage().contains("unexpected CCS")) {
                                            Platform.logEvent(String.format("ssl_unexpected_ccs: host=%s", getHostnameOrIP()));
                                        }
                                        throw e2;
                                    }
                                    synchronized (this.ssl) {
                                        transitionTo(8);
                                        this.ssl.notifyAll();
                                        try {
                                            shutdownAndFreeSslNative();
                                        } catch (IOException unused4) {
                                        }
                                    }
                                }
                            }
                        }
                    } catch (SSLProtocolException e3) {
                        throw ((SSLHandshakeException) new SSLHandshakeException("Handshake failed").initCause(e3));
                    }
                } catch (Throwable th) {
                    if (1 != 0) {
                        synchronized (this.ssl) {
                            transitionTo(8);
                            this.ssl.notifyAll();
                            try {
                                shutdownAndFreeSslNative();
                            } catch (IOException unused5) {
                            }
                        }
                    }
                    throw th;
                }
            }
        }
    }

    @Override
    public final void verifyCertificateChain(byte[][] bArr, String str) {
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    X509Certificate[] decodeX509CertificateChain = SSLUtils.decodeX509CertificateChain(bArr);
                    X509TrustManager x509TrustManager = this.sslParameters.getX509TrustManager();
                    if (x509TrustManager == null) {
                        throw new CertificateException("No X.509 TrustManager");
                    }
                    this.activeSession.onPeerCertificatesReceived(getHostnameOrIP(), getPort(), decodeX509CertificateChain);
                    if (getUseClientMode()) {
                        Platform.checkServerTrusted(x509TrustManager, decodeX509CertificateChain, str, this);
                        return;
                    } else {
                        Platform.checkClientTrusted(x509TrustManager, decodeX509CertificateChain, decodeX509CertificateChain[0].getPublicKey().getAlgorithm(), this);
                        return;
                    }
                }
            } catch (CertificateException e) {
                throw e;
            } catch (Exception e2) {
                throw new CertificateException(e2);
            }
        }
        throw new CertificateException("Peer sent no certificate");
    }
}
