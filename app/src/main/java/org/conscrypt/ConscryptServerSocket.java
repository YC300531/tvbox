package org.conscrypt;

import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;
final class ConscryptServerSocket extends SSLServerSocket {
    private boolean channelIdEnabled;
    private final SSLParametersImpl sslParameters;
    private boolean useEngineSocket;

    public ConscryptServerSocket(int i, int i2, InetAddress inetAddress, SSLParametersImpl sSLParametersImpl) {
        super(i, i2, inetAddress);
        this.sslParameters = sSLParametersImpl;
    }

    public ConscryptServerSocket(int i, int i2, SSLParametersImpl sSLParametersImpl) {
        super(i, i2);
        this.sslParameters = sSLParametersImpl;
    }

    public ConscryptServerSocket(int i, SSLParametersImpl sSLParametersImpl) {
        super(i);
        this.sslParameters = sSLParametersImpl;
    }

    public ConscryptServerSocket(SSLParametersImpl sSLParametersImpl) {
        this.sslParameters = sSLParametersImpl;
    }

    @Override
    public Socket accept() {
        AbstractConscryptSocket createEngineSocket = this.useEngineSocket ? Platform.createEngineSocket(this.sslParameters) : Platform.createFileDescriptorSocket(this.sslParameters);
        createEngineSocket.setChannelIdEnabled(this.channelIdEnabled);
        implAccept(createEngineSocket);
        return createEngineSocket;
    }

    @Override
    public boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override
    public String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    @Override
    public boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override
    public String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override
    public boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override
    public boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    public boolean isChannelIdEnabled() {
        return this.channelIdEnabled;
    }

    public void setChannelIdEnabled(boolean z) {
        this.channelIdEnabled = z;
    }

    @Override
    public void setEnableSessionCreation(boolean z) {
        this.sslParameters.setEnableSessionCreation(z);
    }

    @Override
    public void setEnabledCipherSuites(String[] strArr) {
        this.sslParameters.setEnabledCipherSuites(strArr);
    }

    @Override
    public void setEnabledProtocols(String[] strArr) {
        this.sslParameters.setEnabledProtocols(strArr);
    }

    @Override
    public void setNeedClientAuth(boolean z) {
        this.sslParameters.setNeedClientAuth(z);
    }

    @Override
    public void setUseClientMode(boolean z) {
        this.sslParameters.setUseClientMode(z);
    }

    public ConscryptServerSocket setUseEngineSocket(boolean z) {
        this.useEngineSocket = z;
        return this;
    }

    @Override
    public void setWantClientAuth(boolean z) {
        this.sslParameters.setWantClientAuth(z);
    }
}
