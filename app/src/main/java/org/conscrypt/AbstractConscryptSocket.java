package org.conscrypt;

import java.io.FileDescriptor;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
public abstract class AbstractConscryptSocket extends SSLSocket {
    private final boolean autoClose;
    private final List<HandshakeCompletedListener> listeners;
    private String peerHostname;
    private final PeerInfoProvider peerInfoProvider;
    private final int peerPort;
    private int readTimeoutMilliseconds;
    public final Socket socket;

    public AbstractConscryptSocket() {
        this.peerInfoProvider = new PeerInfoProvider() {
            @Override
            public String getHostname() {
                return getHostname();
            }

            @Override
            public String getHostnameOrIP() {
                return getHostnameOrIP();
            }

            @Override
            public int getPort() {
                return getPort();
            }
        };
        this.listeners = new ArrayList(2);
        this.socket = this;
        this.peerHostname = null;
        this.peerPort = -1;
        this.autoClose = false;
    }

    public AbstractConscryptSocket(String str, int i) {
        super(str, i);
        this.peerInfoProvider = new PeerInfoProvider() {
            @Override
            public String getHostname() {
                return getHostname();
            }

            @Override
            public String getHostnameOrIP() {
                return getHostnameOrIP();
            }

            @Override
            public int getPort() {
                return getPort();
            }
        };
        this.listeners = new ArrayList(2);
        this.socket = this;
        this.peerHostname = str;
        this.peerPort = i;
        this.autoClose = false;
    }

    public AbstractConscryptSocket(String str, int i, InetAddress inetAddress, int i2) {
        super(str, i, inetAddress, i2);
        this.peerInfoProvider = new PeerInfoProvider() {
            @Override
            public String getHostname() {
                return getHostname();
            }

            @Override
            public String getHostnameOrIP() {
                return getHostnameOrIP();
            }

            @Override
            public int getPort() {
                return getPort();
            }
        };
        this.listeners = new ArrayList(2);
        this.socket = this;
        this.peerHostname = str;
        this.peerPort = i;
        this.autoClose = false;
    }

    public AbstractConscryptSocket(InetAddress inetAddress, int i) {
        super(inetAddress, i);
        this.peerInfoProvider = new PeerInfoProvider() {
            @Override
            public String getHostname() {
                return getHostname();
            }

            @Override
            public String getHostnameOrIP() {
                return getHostnameOrIP();
            }

            @Override
            public int getPort() {
                return getPort();
            }
        };
        this.listeners = new ArrayList(2);
        this.socket = this;
        this.peerHostname = null;
        this.peerPort = -1;
        this.autoClose = false;
    }

    public AbstractConscryptSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) {
        super(inetAddress, i, inetAddress2, i2);
        this.peerInfoProvider = new PeerInfoProvider() {
            @Override
            public String getHostname() {
                return getHostname();
            }

            @Override
            public String getHostnameOrIP() {
                return getHostnameOrIP();
            }

            @Override
            public int getPort() {
                return getPort();
            }
        };
        this.listeners = new ArrayList(2);
        this.socket = this;
        this.peerHostname = null;
        this.peerPort = -1;
        this.autoClose = false;
    }

    public AbstractConscryptSocket(Socket socket, String str, int i, boolean z) {
        this.peerInfoProvider = new PeerInfoProvider() {
            @Override
            public String getHostname() {
                return getHostname();
            }

            @Override
            public String getHostnameOrIP() {
                return getHostnameOrIP();
            }

            @Override
            public int getPort() {
                return getPort();
            }
        };
        this.listeners = new ArrayList(2);
        this.socket = (Socket) Preconditions.checkNotNull(socket, "socket");
        this.peerHostname = str;
        this.peerPort = i;
        this.autoClose = z;
    }

    private boolean isDelegating() {
        Socket socket = this.socket;
        return (socket == null || socket == this) ? false : true;
    }

    @Override
    public void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        Preconditions.checkArgument(handshakeCompletedListener != null, "Provided listener is null");
        this.listeners.add(handshakeCompletedListener);
    }

    @Override
    public void bind(SocketAddress socketAddress) {
        if (isDelegating()) {
            this.socket.bind(socketAddress);
        } else {
            super.bind(socketAddress);
        }
    }

    public final void checkOpen() {
        if (isClosed()) {
            throw new SocketException("Socket is closed");
        }
    }

    @Override
    public void close() {
        if (!isDelegating()) {
            if (super.isClosed()) {
                return;
            }
            super.close();
        } else if (!this.autoClose || this.socket.isClosed()) {
        } else {
            this.socket.close();
        }
    }

    @Override
    public final void connect(SocketAddress socketAddress) {
        connect(socketAddress, 0);
    }

    @Override
    public final void connect(SocketAddress socketAddress, int i) {
        if (this.peerHostname == null && (socketAddress instanceof InetSocketAddress)) {
            this.peerHostname = Platform.getHostStringFromInetSocketAddress((InetSocketAddress) socketAddress);
        }
        if (isDelegating()) {
            this.socket.connect(socketAddress, i);
        } else {
            super.connect(socketAddress, i);
        }
    }

    public abstract byte[] exportKeyingMaterial(String str, byte[] bArr, int i);

    public abstract SSLSession getActiveSession();

    @Deprecated
    public abstract byte[] getAlpnSelectedProtocol();

    @Override
    public abstract String getApplicationProtocol();

    public abstract String[] getApplicationProtocols();

    @Override
    public SocketChannel getChannel() {
        return null;
    }

    public abstract byte[] getChannelId();

    public FileDescriptor getFileDescriptor$() {
        return isDelegating() ? Platform.getFileDescriptor(this.socket) : Platform.getFileDescriptorFromSSLSocket(this);
    }

    @Override
    public abstract String getHandshakeApplicationProtocol();

    @Override
    public abstract SSLSession getHandshakeSession();

    public String getHostname() {
        return this.peerHostname;
    }

    public String getHostnameOrIP() {
        String str = this.peerHostname;
        if (str != null) {
            return str;
        }
        InetAddress inetAddress = getInetAddress();
        if (inetAddress != null) {
            return Platform.getOriginalHostNameFromInetAddress(inetAddress);
        }
        return null;
    }

    @Override
    public InetAddress getInetAddress() {
        return isDelegating() ? this.socket.getInetAddress() : super.getInetAddress();
    }

    @Override
    public InputStream getInputStream() {
        return isDelegating() ? this.socket.getInputStream() : super.getInputStream();
    }

    @Override
    public boolean getKeepAlive() {
        return isDelegating() ? this.socket.getKeepAlive() : super.getKeepAlive();
    }

    @Override
    public InetAddress getLocalAddress() {
        return isDelegating() ? this.socket.getLocalAddress() : super.getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        return isDelegating() ? this.socket.getLocalPort() : super.getLocalPort();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        return isDelegating() ? this.socket.getLocalSocketAddress() : super.getLocalSocketAddress();
    }

    @Deprecated
    public byte[] getNpnSelectedProtocol() {
        return null;
    }

    @Override
    public boolean getOOBInline() {
        return false;
    }

    @Override
    public OutputStream getOutputStream() {
        return isDelegating() ? this.socket.getOutputStream() : super.getOutputStream();
    }

    @Override
    public final int getPort() {
        if (isDelegating()) {
            return this.socket.getPort();
        }
        int i = this.peerPort;
        return i != -1 ? i : super.getPort();
    }

    @Override
    public int getReceiveBufferSize() {
        return isDelegating() ? this.socket.getReceiveBufferSize() : super.getReceiveBufferSize();
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        return isDelegating() ? this.socket.getRemoteSocketAddress() : super.getRemoteSocketAddress();
    }

    @Override
    public boolean getReuseAddress() {
        return isDelegating() ? this.socket.getReuseAddress() : super.getReuseAddress();
    }

    @Override
    public int getSendBufferSize() {
        return isDelegating() ? this.socket.getSendBufferSize() : super.getSendBufferSize();
    }

    @Override
    public int getSoLinger() {
        return isDelegating() ? this.socket.getSoLinger() : super.getSoLinger();
    }

    @Override
    public final int getSoTimeout() {
        return isDelegating() ? this.socket.getSoTimeout() : this.readTimeoutMilliseconds;
    }

    public int getSoWriteTimeout() {
        return 0;
    }

    @Override
    public boolean getTcpNoDelay() {
        return isDelegating() ? this.socket.getTcpNoDelay() : super.getTcpNoDelay();
    }

    public abstract byte[] getTlsUnique();

    @Override
    public int getTrafficClass() {
        return isDelegating() ? this.socket.getTrafficClass() : super.getTrafficClass();
    }

    @Override
    public boolean isBound() {
        return isDelegating() ? this.socket.isBound() : super.isBound();
    }

    @Override
    public boolean isClosed() {
        return isDelegating() ? this.socket.isClosed() : super.isClosed();
    }

    @Override
    public boolean isConnected() {
        return isDelegating() ? this.socket.isConnected() : super.isConnected();
    }

    @Override
    public boolean isInputShutdown() {
        return isDelegating() ? this.socket.isInputShutdown() : super.isInputShutdown();
    }

    @Override
    public boolean isOutputShutdown() {
        return isDelegating() ? this.socket.isOutputShutdown() : super.isOutputShutdown();
    }

    public final void notifyHandshakeCompletedListeners() {
        ArrayList arrayList = new ArrayList(this.listeners);
        if (arrayList.isEmpty()) {
            return;
        }
        HandshakeCompletedEvent handshakeCompletedEvent = new HandshakeCompletedEvent(this, getActiveSession());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                ((HandshakeCompletedListener) it.next()).handshakeCompleted(handshakeCompletedEvent);
            } catch (RuntimeException e) {
                Thread currentThread = Thread.currentThread();
                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, e);
            }
        }
    }

    public final PeerInfoProvider peerInfoProvider() {
        return this.peerInfoProvider;
    }

    @Override
    public void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        Preconditions.checkArgument(handshakeCompletedListener != null, "Provided listener is null");
        if (!this.listeners.remove(handshakeCompletedListener)) {
            throw new IllegalArgumentException("Provided listener is not registered");
        }
    }

    @Override
    public final void sendUrgentData(int i) {
        throw new SocketException("Method sendUrgentData() is not supported.");
    }

    @Deprecated
    public abstract void setAlpnProtocols(byte[] bArr);

    @Deprecated
    public abstract void setAlpnProtocols(String[] strArr);

    public abstract void setApplicationProtocolSelector(ApplicationProtocolSelector applicationProtocolSelector);

    public abstract void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter);

    public abstract void setApplicationProtocols(String[] strArr);

    public abstract void setChannelIdEnabled(boolean z);

    public abstract void setChannelIdPrivateKey(PrivateKey privateKey);

    public void setHandshakeTimeout(int i) {
        throw new SocketException("Method setHandshakeTimeout() is not supported.");
    }

    public void setHostname(String str) {
        this.peerHostname = str;
    }

    @Override
    public void setKeepAlive(boolean z) {
        if (isDelegating()) {
            this.socket.setKeepAlive(z);
        } else {
            super.setKeepAlive(z);
        }
    }

    @Deprecated
    public void setNpnProtocols(byte[] bArr) {
    }

    @Override
    public final void setOOBInline(boolean z) {
        throw new SocketException("Method setOOBInline() is not supported.");
    }

    @Override
    public void setPerformancePreferences(int i, int i2, int i3) {
        if (isDelegating()) {
            this.socket.setPerformancePreferences(i, i2, i3);
        } else {
            super.setPerformancePreferences(i, i2, i3);
        }
    }

    @Override
    public void setReceiveBufferSize(int i) {
        if (isDelegating()) {
            this.socket.setReceiveBufferSize(i);
        } else {
            super.setReceiveBufferSize(i);
        }
    }

    @Override
    public void setReuseAddress(boolean z) {
        if (isDelegating()) {
            this.socket.setReuseAddress(z);
        } else {
            super.setReuseAddress(z);
        }
    }

    @Override
    public void setSendBufferSize(int i) {
        if (isDelegating()) {
            this.socket.setSendBufferSize(i);
        } else {
            super.setSendBufferSize(i);
        }
    }

    @Override
    public void setSoLinger(boolean z, int i) {
        if (isDelegating()) {
            this.socket.setSoLinger(z, i);
        } else {
            super.setSoLinger(z, i);
        }
    }

    @Override
    public final void setSoTimeout(int i) {
        if (isDelegating()) {
            this.socket.setSoTimeout(i);
            return;
        }
        super.setSoTimeout(i);
        this.readTimeoutMilliseconds = i;
    }

    public void setSoWriteTimeout(int i) {
        throw new SocketException("Method setSoWriteTimeout() is not supported.");
    }

    @Override
    public void setTcpNoDelay(boolean z) {
        if (isDelegating()) {
            this.socket.setTcpNoDelay(z);
        } else {
            super.setTcpNoDelay(z);
        }
    }

    @Override
    public void setTrafficClass(int i) {
        if (isDelegating()) {
            this.socket.setTrafficClass(i);
        } else {
            super.setTrafficClass(i);
        }
    }

    public abstract void setUseSessionTickets(boolean z);

    @Override
    public void shutdownInput() {
        if (isDelegating()) {
            this.socket.shutdownInput();
        } else {
            super.shutdownInput();
        }
    }

    @Override
    public void shutdownOutput() {
        if (isDelegating()) {
            this.socket.shutdownOutput();
        } else {
            super.shutdownOutput();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SSL socket over ");
        sb.append(isDelegating() ? this.socket.toString() : super.toString());
        return sb.toString();
    }
}
