package org.conscrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
public class TrustManagerFactoryImpl extends TrustManagerFactorySpi {
    private KeyStore keyStore;

    @Override
    public TrustManager[] engineGetTrustManagers() {
        if (this.keyStore != null) {
            return new TrustManager[]{new TrustManagerImpl(this.keyStore)};
        }
        throw new IllegalStateException("TrustManagerFactory is not initialized");
    }

    @Override
    public void engineInit(KeyStore keyStore) {
        if (keyStore == null) {
            keyStore = Platform.getDefaultCertKeyStore();
        }
        this.keyStore = keyStore;
    }

    @Override
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) {
        throw new InvalidAlgorithmParameterException("ManagerFactoryParameters not supported");
    }
}
