package org.conscrypt;

import java.net.Socket;
public class KitKatPlatformOpenSSLSocketAdapterFactory extends BaseOpenSSLSocketAdapterFactory {
    public KitKatPlatformOpenSSLSocketAdapterFactory(OpenSSLSocketFactoryImpl openSSLSocketFactoryImpl) {
        super(openSSLSocketFactoryImpl);
    }

    @Override
    public Socket wrap(OpenSSLSocketImpl openSSLSocketImpl) {
        return new KitKatPlatformOpenSSLSocketImplAdapter(openSSLSocketImpl);
    }
}
