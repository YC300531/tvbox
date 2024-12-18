package org.conscrypt;

import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;
public abstract class BaseOpenSSLSocketAdapterFactory extends SSLSocketFactory {
    private final OpenSSLSocketFactoryImpl delegate;

    public BaseOpenSSLSocketAdapterFactory(OpenSSLSocketFactoryImpl openSSLSocketFactoryImpl) {
        this.delegate = openSSLSocketFactoryImpl;
    }

    @Override
    public Socket createSocket() {
        return wrap((OpenSSLSocketImpl) this.delegate.createSocket());
    }

    @Override
    public Socket createSocket(String str, int i) {
        return wrap((OpenSSLSocketImpl) this.delegate.createSocket(str, i));
    }

    @Override
    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) {
        return wrap((OpenSSLSocketImpl) this.delegate.createSocket(str, i, inetAddress, i2));
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) {
        return wrap((OpenSSLSocketImpl) this.delegate.createSocket(inetAddress, i));
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) {
        return wrap((OpenSSLSocketImpl) this.delegate.createSocket(inetAddress, i, inetAddress2, i2));
    }

    @Override
    public Socket createSocket(Socket socket, String str, int i, boolean z) {
        return wrap((OpenSSLSocketImpl) this.delegate.createSocket(socket, str, i, z));
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return this.delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return this.delegate.getSupportedCipherSuites();
    }

    public abstract Socket wrap(OpenSSLSocketImpl openSSLSocketImpl);
}
