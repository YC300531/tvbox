package org.conscrypt;

import androidx.base.a.b;
import androidx.exifinterface.media.ExifInterface;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.SSLParametersImpl;
public class ConscryptEngineSocket extends OpenSSLSocketImpl implements SSLParametersImpl.AliasChooser {
    private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);
    private BufferAllocator bufferAllocator;
    private final ConscryptEngine engine;
    private final Object handshakeLock;
    private SSLInputStream in;
    private SSLOutputStream out;
    private int state;
    private final Object stateLock;

    public static class fun3 {
        public static final int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;
        public static final int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status;

        static {
            int[] iArr = new int[SSLEngineResult.Status.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$Status = iArr;
            try {
                iArr[SSLEngineResult.Status.BUFFER_UNDERFLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.OK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.CLOSED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[SSLEngineResult.HandshakeStatus.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = iArr2;
            try {
                iArr2[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public final class SSLInputStream extends InputStream {
        private final AllocatedBuffer allocatedBuffer;
        private final ByteBuffer fromEngine;
        private final ByteBuffer fromSocket;
        private final int fromSocketArrayOffset;
        private final Object readLock = new Object();
        private final byte[] singleByte = new byte[1];
        private InputStream socketInputStream;

        public SSLInputStream() {
            ByteBuffer allocateDirect;
            if (bufferAllocator != null) {
                AllocatedBuffer allocateDirectBuffer = bufferAllocator.allocateDirectBuffer(engine.getSession().getApplicationBufferSize());
                this.allocatedBuffer = allocateDirectBuffer;
                allocateDirect = allocateDirectBuffer.nioBuffer();
            } else {
                this.allocatedBuffer = null;
                allocateDirect = ByteBuffer.allocateDirect(engine.getSession().getApplicationBufferSize());
            }
            this.fromEngine = allocateDirect;
            this.fromEngine.flip();
            ByteBuffer allocate = ByteBuffer.allocate(engine.getSession().getPacketBufferSize());
            this.fromSocket = allocate;
            this.fromSocketArrayOffset = allocate.arrayOffset();
        }

        private void init() {
            if (this.socketInputStream == null) {
                this.socketInputStream = getUnderlyingInputStream();
            }
        }

        private boolean isHandshakeFinished() {
            boolean z;
            synchronized (stateLock) {
                z = state >= 4;
            }
            return z;
        }

        private boolean isHandshaking(SSLEngineResult.HandshakeStatus handshakeStatus) {
            int i = fun3.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[handshakeStatus.ordinal()];
            return i == 1 || i == 2 || i == 3;
        }

        public int processDataFromSocket(byte[] r7, int r8, int r9) {
            
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return 1;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: org.conscrypt.ConscryptEngineSocket.SSLInputStream.processDataFromSocket(byte[], int, int):int");
        }

        private int readFromSocket() {
            try {
                int position = this.fromSocket.position();
                int read = this.socketInputStream.read(this.fromSocket.array(), this.fromSocketArrayOffset + position, this.fromSocket.limit() - position);
                if (read > 0) {
                    this.fromSocket.position(position + read);
                }
                return read;
            } catch (EOFException unused) {
                return -1;
            }
        }

        private int readUntilDataAvailable(byte[] bArr, int i, int i2) {
            int processDataFromSocket;
            do {
                processDataFromSocket = processDataFromSocket(bArr, i, i2);
            } while (processDataFromSocket == 0);
            return processDataFromSocket;
        }

        private void renegotiate() {
            synchronized (handshakeLock) {
                doHandshake();
            }
        }

        @Override
        public int available() {
            int remaining;
            startHandshake();
            synchronized (this.readLock) {
                init();
                remaining = this.fromEngine.remaining();
            }
            return remaining;
        }

        @Override
        public void close() {
            close();
        }

        @Override
        public int read() {
            startHandshake();
            synchronized (this.readLock) {
                int read = read(this.singleByte, 0, 1);
                if (read == -1) {
                    return -1;
                }
                if (read == 1) {
                    return this.singleByte[0] & ExifInterface.MARKER;
                }
                throw new SSLException("read incorrect number of bytes " + read);
            }
        }

        @Override
        public int read(byte[] bArr) {
            int read;
            startHandshake();
            synchronized (this.readLock) {
                read = read(bArr, 0, bArr.length);
            }
            return read;
        }

        @Override
        public int read(byte[] bArr, int i, int i2) {
            int readUntilDataAvailable;
            startHandshake();
            synchronized (this.readLock) {
                readUntilDataAvailable = readUntilDataAvailable(bArr, i, i2);
            }
            return readUntilDataAvailable;
        }

        public void release() {
            synchronized (this.readLock) {
                AllocatedBuffer allocatedBuffer = this.allocatedBuffer;
                if (allocatedBuffer != null) {
                    allocatedBuffer.release();
                }
            }
        }
    }

    public final class SSLOutputStream extends OutputStream {
        private OutputStream socketOutputStream;
        private final ByteBuffer target;
        private final int targetArrayOffset;
        private final Object writeLock = new Object();

        public SSLOutputStream() {
            ByteBuffer allocate = ByteBuffer.allocate(engine.getSession().getPacketBufferSize());
            this.target = allocate;
            this.targetArrayOffset = allocate.arrayOffset();
        }

        public void flushInternal() {
            checkOpen();
            init();
            this.socketOutputStream.flush();
        }

        private void init() {
            if (this.socketOutputStream == null) {
                this.socketOutputStream = getUnderlyingOutputStream();
            }
        }

        public void writeInternal(ByteBuffer byteBuffer) {
            Platform.blockGuardOnNetwork();
            checkOpen();
            init();
            int remaining = byteBuffer.remaining();
            do {
                this.target.clear();
                SSLEngineResult wrap = engine.wrap(byteBuffer, this.target);
                if (wrap.getStatus() != SSLEngineResult.Status.OK && wrap.getStatus() != SSLEngineResult.Status.CLOSED) {
                    StringBuilder d = b.d("Unexpected engine result ");
                    d.append(wrap.getStatus());
                    throw new SSLException(d.toString());
                } else if (this.target.position() != wrap.bytesProduced()) {
                    StringBuilder d2 = b.d("Engine bytesProduced ");
                    d2.append(wrap.bytesProduced());
                    d2.append(" does not match bytes written ");
                    d2.append(this.target.position());
                    throw new SSLException(d2.toString());
                } else {
                    remaining -= wrap.bytesConsumed();
                    if (remaining != byteBuffer.remaining()) {
                        throw new SSLException("Engine did not read the correct number of bytes");
                    }
                    if (wrap.getStatus() == SSLEngineResult.Status.CLOSED && wrap.bytesProduced() == 0) {
                        if (remaining > 0) {
                            throw new SocketException("Socket closed");
                        }
                        return;
                    }
                    this.target.flip();
                    writeToSocket();
                }
            } while (remaining > 0);
        }

        private void writeToSocket() {
            this.socketOutputStream.write(this.target.array(), this.targetArrayOffset, this.target.limit());
        }

        @Override
        public void close() {
            close();
        }

        @Override
        public void flush() {
            startHandshake();
            synchronized (this.writeLock) {
                flushInternal();
            }
        }

        @Override
        public void write(int i) {
            startHandshake();
            synchronized (this.writeLock) {
                write(new byte[]{(byte) i});
            }
        }

        @Override
        public void write(byte[] bArr) {
            startHandshake();
            synchronized (this.writeLock) {
                writeInternal(ByteBuffer.wrap(bArr));
            }
        }

        @Override
        public void write(byte[] bArr, int i, int i2) {
            startHandshake();
            synchronized (this.writeLock) {
                writeInternal(ByteBuffer.wrap(bArr, i, i2));
            }
        }
    }

    public ConscryptEngineSocket(String str, int i, InetAddress inetAddress, int i2, SSLParametersImpl sSLParametersImpl) {
        super(str, i, inetAddress, i2);
        this.stateLock = new Object();
        this.handshakeLock = new Object();
        this.bufferAllocator = ConscryptEngine.getDefaultBufferAllocator();
        this.state = 0;
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(String str, int i, SSLParametersImpl sSLParametersImpl) {
        super(str, i);
        this.stateLock = new Object();
        this.handshakeLock = new Object();
        this.bufferAllocator = ConscryptEngine.getDefaultBufferAllocator();
        this.state = 0;
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2, SSLParametersImpl sSLParametersImpl) {
        super(inetAddress, i, inetAddress2, i2);
        this.stateLock = new Object();
        this.handshakeLock = new Object();
        this.bufferAllocator = ConscryptEngine.getDefaultBufferAllocator();
        this.state = 0;
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(InetAddress inetAddress, int i, SSLParametersImpl sSLParametersImpl) {
        super(inetAddress, i);
        this.stateLock = new Object();
        this.handshakeLock = new Object();
        this.bufferAllocator = ConscryptEngine.getDefaultBufferAllocator();
        this.state = 0;
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(Socket socket, String str, int i, boolean z, SSLParametersImpl sSLParametersImpl) {
        super(socket, str, i, z);
        this.stateLock = new Object();
        this.handshakeLock = new Object();
        this.bufferAllocator = ConscryptEngine.getDefaultBufferAllocator();
        this.state = 0;
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(SSLParametersImpl sSLParametersImpl) {
        this.stateLock = new Object();
        this.handshakeLock = new Object();
        this.bufferAllocator = ConscryptEngine.getDefaultBufferAllocator();
        this.state = 0;
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public void doHandshake() {
        boolean z = false;
        while (!z) {
            try {
                int i = fun3.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[this.engine.getHandshakeStatus().ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        this.out.writeInternal(EMPTY_BUFFER);
                        this.out.flushInternal();
                    } else if (i == 3) {
                        throw new IllegalStateException("Engine tasks are unsupported");
                    } else {
                        if (i != 4 && i != 5) {
                            throw new IllegalStateException("Unknown handshake status: " + this.engine.getHandshakeStatus());
                        }
                        z = true;
                    }
                } else if (this.in.processDataFromSocket(EmptyArray.BYTE, 0, 0) < 0) {
                    throw SSLUtils.toSSLHandshakeException(new EOFException("connection closed"));
                }
            } catch (SSLException e) {
                drainOutgoingQueue();
                close();
                throw e;
            } catch (IOException e2) {
                close();
                throw e2;
            } catch (Exception e3) {
                close();
                throw SSLUtils.toSSLHandshakeException(e3);
            }
        }
    }

    private void drainOutgoingQueue() {
        while (this.engine.pendingOutboundEncryptedBytes() > 0) {
            try {
                this.out.writeInternal(EMPTY_BUFFER);
                this.out.flushInternal();
            } catch (IOException unused) {
                return;
            }
        }
    }

    private static X509TrustManager getDelegatingTrustManager(X509TrustManager x509TrustManager, final ConscryptEngineSocket conscryptEngineSocket) {
        if (x509TrustManager instanceof X509ExtendedTrustManager) {
            final X509ExtendedTrustManager x509ExtendedTrustManager = (X509ExtendedTrustManager) x509TrustManager;
            return new X509ExtendedTrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
                    x509ExtendedTrustManager.checkClientTrusted(x509CertificateArr, str);
                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) {
                    throw new AssertionError("Should not be called");
                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) {
                    x509ExtendedTrustManager.checkClientTrusted(x509CertificateArr, str, conscryptEngineSocket);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
                    x509ExtendedTrustManager.checkServerTrusted(x509CertificateArr, str);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) {
                    throw new AssertionError("Should not be called");
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) {
                    x509ExtendedTrustManager.checkServerTrusted(x509CertificateArr, str, conscryptEngineSocket);
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return x509ExtendedTrustManager.getAcceptedIssuers();
                }
            };
        }
        return x509TrustManager;
    }

    public InputStream getUnderlyingInputStream() {
        return super.getInputStream();
    }

    public OutputStream getUnderlyingOutputStream() {
        return super.getOutputStream();
    }

    private static ConscryptEngine newEngine(SSLParametersImpl sSLParametersImpl, ConscryptEngineSocket conscryptEngineSocket) {
        ConscryptEngine conscryptEngine = new ConscryptEngine(Platform.supportsX509ExtendedTrustManager() ? sSLParametersImpl.cloneWithTrustManager(getDelegatingTrustManager(sSLParametersImpl.getX509TrustManager(), conscryptEngineSocket)) : sSLParametersImpl, conscryptEngineSocket.peerInfoProvider(), conscryptEngineSocket);
        conscryptEngine.setHandshakeListener(new HandshakeListener() {
            @Override
            public void onHandshakeFinished() {
                onHandshakeFinished();
            }
        });
        conscryptEngine.setUseClientMode(sSLParametersImpl.getUseClientMode());
        return conscryptEngine;
    }

    public void onHandshakeFinished() {
        boolean z;
        synchronized (this.stateLock) {
            int i = this.state;
            if (i != 8) {
                if (i == 2) {
                    this.state = 4;
                } else if (i == 3) {
                    this.state = 5;
                }
                this.stateLock.notifyAll();
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            notifyHandshakeCompletedListeners();
        }
    }

    private void waitForHandshake() {
        startHandshake();
        synchronized (this.stateLock) {
            while (true) {
                int i = this.state;
                if (i == 5 || i == 4 || i == 8) {
                    break;
                }
                try {
                    this.stateLock.wait();
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
    public final String chooseServerAlias(X509KeyManager x509KeyManager, String str) {
        return x509KeyManager.chooseServerAlias(str, null, this);
    }

    @Override
    public final void close() {
        Object obj = this.stateLock;
        if (obj == null) {
            return;
        }
        synchronized (obj) {
            int i = this.state;
            if (i == 8) {
                return;
            }
            this.state = 8;
            this.stateLock.notifyAll();
            try {
                this.engine.closeInbound();
                this.engine.closeOutbound();
                if (i >= 2) {
                    drainOutgoingQueue();
                    this.engine.closeOutbound();
                }
                try {
                    super.close();
                    SSLInputStream sSLInputStream = this.in;
                    if (sSLInputStream != null) {
                        sSLInputStream.release();
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    super.close();
                    SSLInputStream sSLInputStream2 = this.in;
                    if (sSLInputStream2 != null) {
                        sSLInputStream2.release();
                    }
                    throw th;
                } finally {
                }
            }
        }
    }

    @Override
    public byte[] exportKeyingMaterial(String str, byte[] bArr, int i) {
        return this.engine.exportKeyingMaterial(str, bArr, i);
    }

    @Override
    public final SSLSession getActiveSession() {
        return this.engine.getSession();
    }

    @Override
    public final String getApplicationProtocol() {
        return this.engine.getApplicationProtocol();
    }

    @Override
    public final String[] getApplicationProtocols() {
        return this.engine.getApplicationProtocols();
    }

    @Override
    public final byte[] getChannelId() {
        return this.engine.getChannelId();
    }

    @Override
    public final boolean getEnableSessionCreation() {
        return this.engine.getEnableSessionCreation();
    }

    @Override
    public final String[] getEnabledCipherSuites() {
        return this.engine.getEnabledCipherSuites();
    }

    @Override
    public final String[] getEnabledProtocols() {
        return this.engine.getEnabledProtocols();
    }

    @Override
    public final String getHandshakeApplicationProtocol() {
        return this.engine.getHandshakeApplicationProtocol();
    }

    @Override
    public final SSLSession getHandshakeSession() {
        return this.engine.handshakeSession();
    }

    @Override
    public final InputStream getInputStream() {
        checkOpen();
        waitForHandshake();
        return this.in;
    }

    @Override
    public final boolean getNeedClientAuth() {
        return this.engine.getNeedClientAuth();
    }

    @Override
    public final OutputStream getOutputStream() {
        checkOpen();
        waitForHandshake();
        return this.out;
    }

    @Override
    public final SSLParameters getSSLParameters() {
        return this.engine.getSSLParameters();
    }

    @Override
    public final SSLSession getSession() {
        if (isConnected()) {
            try {
                waitForHandshake();
            } catch (IOException unused) {
            }
        }
        return this.engine.getSession();
    }

    @Override
    public final String[] getSupportedCipherSuites() {
        return this.engine.getSupportedCipherSuites();
    }

    @Override
    public final String[] getSupportedProtocols() {
        return this.engine.getSupportedProtocols();
    }

    @Override
    public byte[] getTlsUnique() {
        return this.engine.getTlsUnique();
    }

    @Override
    public final boolean getUseClientMode() {
        return this.engine.getUseClientMode();
    }

    @Override
    public final boolean getWantClientAuth() {
        return this.engine.getWantClientAuth();
    }

    @Override
    public final void setApplicationProtocolSelector(ApplicationProtocolSelector applicationProtocolSelector) {
        setApplicationProtocolSelector(applicationProtocolSelector == null ? null : new ApplicationProtocolSelectorAdapter(this, applicationProtocolSelector));
    }

    @Override
    public final void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter) {
        this.engine.setApplicationProtocolSelector(applicationProtocolSelectorAdapter);
    }

    @Override
    public final void setApplicationProtocols(String[] strArr) {
        this.engine.setApplicationProtocols(strArr);
    }

    public void setBufferAllocator(BufferAllocator bufferAllocator) {
        this.engine.setBufferAllocator(bufferAllocator);
        this.bufferAllocator = bufferAllocator;
    }

    @Override
    public final void setChannelIdEnabled(boolean z) {
        this.engine.setChannelIdEnabled(z);
    }

    @Override
    public final void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.engine.setChannelIdPrivateKey(privateKey);
    }

    @Override
    public final void setEnableSessionCreation(boolean z) {
        this.engine.setEnableSessionCreation(z);
    }

    @Override
    public final void setEnabledCipherSuites(String[] strArr) {
        this.engine.setEnabledCipherSuites(strArr);
    }

    @Override
    public final void setEnabledProtocols(String[] strArr) {
        this.engine.setEnabledProtocols(strArr);
    }

    @Override
    public void setHandshakeTimeout(int i) {
    }

    @Override
    public final void setHostname(String str) {
        this.engine.setHostname(str);
        super.setHostname(str);
    }

    @Override
    public final void setNeedClientAuth(boolean z) {
        this.engine.setNeedClientAuth(z);
    }

    @Override
    public final void setSSLParameters(SSLParameters sSLParameters) {
        this.engine.setSSLParameters(sSLParameters);
    }

    @Override
    public final void setUseClientMode(boolean z) {
        this.engine.setUseClientMode(z);
    }

    @Override
    public final void setUseSessionTickets(boolean z) {
        this.engine.setUseSessionTickets(z);
    }

    @Override
    public final void setWantClientAuth(boolean z) {
        this.engine.setWantClientAuth(z);
    }

    @Override
    public final void startHandshake() {
        checkOpen();
        try {
            synchronized (this.handshakeLock) {
                synchronized (this.stateLock) {
                    if (this.state == 0) {
                        this.state = 2;
                        this.engine.beginHandshake();
                        this.in = new SSLInputStream();
                        this.out = new SSLOutputStream();
                        doHandshake();
                    }
                }
            }
        } catch (SSLException e) {
            close();
            throw e;
        } catch (IOException e2) {
            close();
            throw e2;
        } catch (Exception e3) {
            close();
            throw SSLUtils.toSSLHandshakeException(e3);
        }
    }
}
