package org.conscrypt;

import java.io.FileDescriptor;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.security.PrivateKey;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
public abstract class OpenSSLSocketImpl extends AbstractConscryptSocket {
    public OpenSSLSocketImpl() {
    }

    public OpenSSLSocketImpl(String str, int i) {
        super(str, i);
    }

    public OpenSSLSocketImpl(String str, int i, InetAddress inetAddress, int i2) {
        super(str, i, inetAddress, i2);
    }

    public OpenSSLSocketImpl(InetAddress inetAddress, int i) {
        super(inetAddress, i);
    }

    public OpenSSLSocketImpl(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) {
        super(inetAddress, i, inetAddress2, i2);
    }

    public OpenSSLSocketImpl(Socket socket, String str, int i, boolean z) {
        super(socket, str, i, z);
    }

    @Override
    public void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        super.addHandshakeCompletedListener(handshakeCompletedListener);
    }

    @Override
    public void bind(SocketAddress socketAddress) {
        super.bind(socketAddress);
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    @Deprecated
    public final byte[] getAlpnSelectedProtocol() {
        return SSLUtils.toProtocolBytes(getApplicationProtocol());
    }

    @Override
    public SocketChannel getChannel() {
        return super.getChannel();
    }

    @Override
    public abstract byte[] getChannelId();

    @Override
    public FileDescriptor getFileDescriptor$() {
        return super.getFileDescriptor$();
    }

    @Override
    public abstract SSLSession getHandshakeSession();

    @Override
    public String getHostname() {
        return super.getHostname();
    }

    @Override
    public String getHostnameOrIP() {
        return super.getHostnameOrIP();
    }

    @Override
    public InetAddress getInetAddress() {
        return super.getInetAddress();
    }

    @Override
    public InputStream getInputStream() {
        return super.getInputStream();
    }

    @Override
    public boolean getKeepAlive() {
        return super.getKeepAlive();
    }

    @Override
    public InetAddress getLocalAddress() {
        return super.getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        return super.getLocalPort();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        return super.getLocalSocketAddress();
    }

    @Override
    @Deprecated
    public final byte[] getNpnSelectedProtocol() {
        return super.getNpnSelectedProtocol();
    }

    @Override
    public boolean getOOBInline() {
        return super.getOOBInline();
    }

    @Override
    public OutputStream getOutputStream() {
        return super.getOutputStream();
    }

    @Override
    public int getReceiveBufferSize() {
        return super.getReceiveBufferSize();
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        return super.getRemoteSocketAddress();
    }

    @Override
    public boolean getReuseAddress() {
        return super.getReuseAddress();
    }

    @Override
    public int getSendBufferSize() {
        return super.getSendBufferSize();
    }

    @Override
    public int getSoLinger() {
        return super.getSoLinger();
    }

    @Override
    public int getSoWriteTimeout() {
        return super.getSoWriteTimeout();
    }

    @Override
    public boolean getTcpNoDelay() {
        return super.getTcpNoDelay();
    }

    @Override
    public int getTrafficClass() {
        return super.getTrafficClass();
    }

    @Override
    public boolean isBound() {
        return super.isBound();
    }

    @Override
    public boolean isClosed() {
        return super.isClosed();
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

    @Override
    public boolean isInputShutdown() {
        return super.isInputShutdown();
    }

    @Override
    public boolean isOutputShutdown() {
        return super.isOutputShutdown();
    }

    @Override
    public void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        super.removeHandshakeCompletedListener(handshakeCompletedListener);
    }

    @Override
    @Deprecated
    public final void setAlpnProtocols(byte[] bArr) {
        if (bArr == null) {
            bArr = EmptyArray.BYTE;
        }
        setApplicationProtocols(SSLUtils.decodeProtocols(bArr));
    }

    @Override
    @Deprecated
    public final void setAlpnProtocols(String[] strArr) {
        if (strArr == null) {
            strArr = EmptyArray.STRING;
        }
        setApplicationProtocols(strArr);
    }

    @Override
    public abstract void setChannelIdEnabled(boolean z);

    @Override
    public abstract void setChannelIdPrivateKey(PrivateKey privateKey);

    @Override
    public void setHandshakeTimeout(int i) {
        super.setHandshakeTimeout(i);
    }

    @Override
    public void setHostname(String str) {
        super.setHostname(str);
    }

    @Override
    public void setKeepAlive(boolean z) {
        super.setKeepAlive(z);
    }

    @Override
    @Deprecated
    public final void setNpnProtocols(byte[] bArr) {
        super.setNpnProtocols(bArr);
    }

    @Override
    public void setPerformancePreferences(int i, int i2, int i3) {
        super.setPerformancePreferences(i, i2, i3);
    }

    @Override
    public void setReceiveBufferSize(int i) {
        super.setReceiveBufferSize(i);
    }

    @Override
    public void setReuseAddress(boolean z) {
        super.setReuseAddress(z);
    }

    @Override
    public void setSendBufferSize(int i) {
        super.setSendBufferSize(i);
    }

    @Override
    public void setSoLinger(boolean z, int i) {
        super.setSoLinger(z, i);
    }

    @Override
    public void setSoWriteTimeout(int i) {
        super.setSoWriteTimeout(i);
    }

    @Override
    public void setTcpNoDelay(boolean z) {
        super.setTcpNoDelay(z);
    }

    @Override
    public void setTrafficClass(int i) {
        super.setTrafficClass(i);
    }

    @Override
    public abstract void setUseSessionTickets(boolean z);

    @Override
    public void shutdownInput() {
        super.shutdownInput();
    }

    @Override
    public void shutdownOutput() {
        super.shutdownOutput();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
