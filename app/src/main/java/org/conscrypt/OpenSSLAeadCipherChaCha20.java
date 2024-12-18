package org.conscrypt;

import androidx.base.a.a;
import androidx.base.a.b;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.conscrypt.OpenSSLCipher;
public class OpenSSLAeadCipherChaCha20 extends OpenSSLAeadCipher {
    public OpenSSLAeadCipherChaCha20() {
        super(OpenSSLCipher.Mode.POLY1305);
    }

    @Override
    public void checkSupportedKeySize(int i) {
        if (i != 32) {
            throw new InvalidKeyException(b.a("Unsupported key size: ", i, " bytes (must be 32)"));
        }
    }

    @Override
    public void checkSupportedMode(OpenSSLCipher.Mode mode) {
        if (mode != OpenSSLCipher.Mode.POLY1305) {
            throw new NoSuchAlgorithmException("Mode must be Poly1305");
        }
    }

    @Override
    public String getBaseCipherName() {
        return "ChaCha20";
    }

    @Override
    public int getCipherBlockSize() {
        return 0;
    }

    @Override
    public long getEVP_AEAD(int i) {
        if (i == 32) {
            return NativeCrypto.EVP_aead_chacha20_poly1305();
        }
        throw new RuntimeException(a.h("Unexpected key length: ", i));
    }

    @Override
    public int getOutputSizeForFinal(int i) {
        return isEncrypting() ? this.bufCount + i + 16 : Math.max(0, (this.bufCount + i) - 16);
    }
}
