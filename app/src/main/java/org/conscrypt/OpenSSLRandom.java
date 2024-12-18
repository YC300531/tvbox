package org.conscrypt;

import java.security.SecureRandomSpi;
import java.util.Objects;
public final class OpenSSLRandom extends SecureRandomSpi {
    private static final long serialVersionUID = 8506210602917522861L;

    @Override
    public byte[] engineGenerateSeed(int i) {
        byte[] bArr = new byte[i];
        NativeCrypto.RAND_bytes(bArr);
        return bArr;
    }

    @Override
    public void engineNextBytes(byte[] bArr) {
        NativeCrypto.RAND_bytes(bArr);
    }

    @Override
    public void engineSetSeed(byte[] bArr) {
        Objects.requireNonNull(bArr, "seed == null");
    }
}
