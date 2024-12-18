package org.conscrypt;

import androidx.base.a.b;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import org.conscrypt.OpenSSLCipher;
public class OpenSSLEvpCipherARC4 extends OpenSSLEvpCipher {
    public OpenSSLEvpCipherARC4() {
        super(OpenSSLCipher.Mode.ECB, OpenSSLCipher.Padding.NOPADDING);
    }

    @Override
    public void checkSupportedKeySize(int i) {
    }

    @Override
    public void checkSupportedMode(OpenSSLCipher.Mode mode) {
        if (mode == OpenSSLCipher.Mode.NONE || mode == OpenSSLCipher.Mode.ECB) {
            return;
        }
        StringBuilder d = b.d("Unsupported mode ");
        d.append(mode.toString());
        throw new NoSuchAlgorithmException(d.toString());
    }

    @Override
    public void checkSupportedPadding(OpenSSLCipher.Padding padding) {
        if (padding == OpenSSLCipher.Padding.NOPADDING) {
            return;
        }
        StringBuilder d = b.d("Unsupported padding ");
        d.append(padding.toString());
        throw new NoSuchPaddingException(d.toString());
    }

    @Override
    public String getBaseCipherName() {
        return "ARCFOUR";
    }

    @Override
    public int getCipherBlockSize() {
        return 0;
    }

    @Override
    public String getCipherName(int i, OpenSSLCipher.Mode mode) {
        return "rc4";
    }

    @Override
    public boolean supportsVariableSizeKey() {
        return true;
    }
}
