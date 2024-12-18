package org.conscrypt;

import androidx.base.a.a;
import androidx.base.a.b;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.conscrypt.OpenSSLCipher;
public abstract class OpenSSLAeadCipherAES extends OpenSSLAeadCipher {
    private static final int AES_BLOCK_SIZE = 16;

    public static class GCM extends OpenSSLAeadCipherAES {

        public static class AES_128 extends GCM {
            @Override
            public void checkSupportedKeySize(int i) {
                if (i != 16) {
                    throw new InvalidKeyException(b.a("Unsupported key size: ", i, " bytes (must be 16)"));
                }
            }
        }

        public static class AES_256 extends GCM {
            @Override
            public void checkSupportedKeySize(int i) {
                if (i != 32) {
                    throw new InvalidKeyException(b.a("Unsupported key size: ", i, " bytes (must be 32)"));
                }
            }
        }

        public GCM() {
            super(OpenSSLCipher.Mode.GCM);
        }

        @Override
        public void checkSupportedMode(OpenSSLCipher.Mode mode) {
            if (mode != OpenSSLCipher.Mode.GCM) {
                throw new NoSuchAlgorithmException("Mode must be GCM");
            }
        }

        @Override
        public long getEVP_AEAD(int i) {
            if (i == 16) {
                return NativeCrypto.EVP_aead_aes_128_gcm();
            }
            if (i == 32) {
                return NativeCrypto.EVP_aead_aes_256_gcm();
            }
            throw new RuntimeException(a.h("Unexpected key length: ", i));
        }
    }

    public static class GCM_SIV extends OpenSSLAeadCipherAES {

        public static class AES_128 extends GCM_SIV {
            @Override
            public void checkSupportedKeySize(int i) {
                if (i != 16) {
                    throw new InvalidKeyException(b.a("Unsupported key size: ", i, " bytes (must be 16)"));
                }
            }
        }

        public static class AES_256 extends GCM_SIV {
            @Override
            public void checkSupportedKeySize(int i) {
                if (i != 32) {
                    throw new InvalidKeyException(b.a("Unsupported key size: ", i, " bytes (must be 32)"));
                }
            }
        }

        public GCM_SIV() {
            super(OpenSSLCipher.Mode.GCM_SIV);
        }

        @Override
        public boolean allowsNonceReuse() {
            return true;
        }

        @Override
        public void checkSupportedMode(OpenSSLCipher.Mode mode) {
            if (mode != OpenSSLCipher.Mode.GCM_SIV) {
                throw new NoSuchAlgorithmException("Mode must be GCM-SIV");
            }
        }

        @Override
        public void checkSupportedTagLength(int i) {
            if (i != 128) {
                throw new InvalidAlgorithmParameterException("Tag length must be 128 bits");
            }
        }

        @Override
        public long getEVP_AEAD(int i) {
            if (i == 16) {
                return NativeCrypto.EVP_aead_aes_128_gcm_siv();
            }
            if (i == 32) {
                return NativeCrypto.EVP_aead_aes_256_gcm_siv();
            }
            throw new RuntimeException(a.h("Unexpected key length: ", i));
        }
    }

    public OpenSSLAeadCipherAES(OpenSSLCipher.Mode mode) {
        super(mode);
    }

    @Override
    public void checkSupportedKeySize(int i) {
        if (i != 16 && i != 32) {
            throw new InvalidKeyException(b.a("Unsupported key size: ", i, " bytes (must be 16 or 32)"));
        }
    }

    @Override
    public AlgorithmParameters engineGetParameters() {
        byte[] bArr = this.iv;
        if (bArr == null) {
            return null;
        }
        AlgorithmParameterSpec gCMParameterSpec = Platform.toGCMParameterSpec(this.tagLengthInBytes * 8, bArr);
        if (gCMParameterSpec == null) {
            return super.engineGetParameters();
        }
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("GCM");
            algorithmParameters.init(gCMParameterSpec);
            return algorithmParameters;
        } catch (NoSuchAlgorithmException e) {
            throw ((Error) new AssertionError("GCM not supported").initCause(e));
        } catch (InvalidParameterSpecException unused) {
            return null;
        }
    }

    @Override
    public String getBaseCipherName() {
        return "AES";
    }

    @Override
    public int getCipherBlockSize() {
        return 16;
    }

    @Override
    public int getOutputSizeForFinal(int i) {
        return isEncrypting() ? this.bufCount + i + this.tagLengthInBytes : Math.max(0, (this.bufCount + i) - this.tagLengthInBytes);
    }

    @Override
    public AlgorithmParameterSpec getParameterSpec(AlgorithmParameters algorithmParameters) {
        if (algorithmParameters != null) {
            AlgorithmParameterSpec fromGCMParameters = Platform.fromGCMParameters(algorithmParameters);
            return fromGCMParameters != null ? fromGCMParameters : super.getParameterSpec(algorithmParameters);
        }
        return null;
    }
}
