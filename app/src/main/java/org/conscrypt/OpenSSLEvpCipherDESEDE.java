package org.conscrypt;

import androidx.base.a.b;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.crypto.NoSuchPaddingException;
import org.conscrypt.OpenSSLCipher;
public abstract class OpenSSLEvpCipherDESEDE extends OpenSSLEvpCipher {
    private static final int DES_BLOCK_SIZE = 8;

    public static class fun1 {
        public static final int[] $SwitchMap$org$conscrypt$OpenSSLCipher$Padding;

        static {
            int[] iArr = new int[OpenSSLCipher.Padding.values().length];
            $SwitchMap$org$conscrypt$OpenSSLCipher$Padding = iArr;
            try {
                iArr[OpenSSLCipher.Padding.NOPADDING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$conscrypt$OpenSSLCipher$Padding[OpenSSLCipher.Padding.PKCS5PADDING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class CBC extends OpenSSLEvpCipherDESEDE {

        public static class NoPadding extends CBC {
            public NoPadding() {
                super(OpenSSLCipher.Padding.NOPADDING);
            }
        }

        public static class PKCS5Padding extends CBC {
            public PKCS5Padding() {
                super(OpenSSLCipher.Padding.PKCS5PADDING);
            }
        }

        public CBC(OpenSSLCipher.Padding padding) {
            super(OpenSSLCipher.Mode.CBC, padding);
        }
    }

    public OpenSSLEvpCipherDESEDE(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
        super(mode, padding);
    }

    @Override
    public void checkSupportedKeySize(int i) {
        if (i != 16 && i != 24) {
            throw new InvalidKeyException("key size must be 128 or 192 bits");
        }
    }

    @Override
    public void checkSupportedMode(OpenSSLCipher.Mode mode) {
        if (mode == OpenSSLCipher.Mode.CBC) {
            return;
        }
        StringBuilder d = b.d("Unsupported mode ");
        d.append(mode.toString());
        throw new NoSuchAlgorithmException(d.toString());
    }

    @Override
    public void checkSupportedPadding(OpenSSLCipher.Padding padding) {
        int i = fun1.$SwitchMap$org$conscrypt$OpenSSLCipher$Padding[padding.ordinal()];
        if (i == 1 || i == 2) {
            return;
        }
        StringBuilder d = b.d("Unsupported padding ");
        d.append(padding.toString());
        throw new NoSuchPaddingException(d.toString());
    }

    @Override
    public String getBaseCipherName() {
        return "DESede";
    }

    @Override
    public int getCipherBlockSize() {
        return 8;
    }

    @Override
    public String getCipherName(int i, OpenSSLCipher.Mode mode) {
        StringBuilder e = b.e(i == 16 ? "des-ede" : "des-ede3", "-");
        e.append(mode.toString().toLowerCase(Locale.US));
        return e.toString();
    }
}
