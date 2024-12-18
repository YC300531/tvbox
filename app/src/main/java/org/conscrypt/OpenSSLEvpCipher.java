package org.conscrypt;

import androidx.base.a.a;
import androidx.base.r5.b;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLCipher;
public abstract class OpenSSLEvpCipher extends OpenSSLCipher {
    private boolean calledUpdate;
    private final NativeRef.EVP_CIPHER_CTX cipherCtx;
    private int modeBlockSize;

    public OpenSSLEvpCipher(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
        super(mode, padding);
        this.cipherCtx = new NativeRef.EVP_CIPHER_CTX(NativeCrypto.EVP_CIPHER_CTX_new());
    }

    private void reset() {
        NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, 0L, this.encodedKey, this.iv, isEncrypting());
        this.calledUpdate = false;
    }

    @Override
    public int doFinalInternal(byte[] bArr, int i, int i2) {
        int i3;
        if (isEncrypting() || this.calledUpdate) {
            int length = bArr.length - i;
            if (length >= i2) {
                i3 = NativeCrypto.EVP_CipherFinal_ex(this.cipherCtx, bArr, i);
            } else {
                byte[] bArr2 = new byte[i2];
                int EVP_CipherFinal_ex = NativeCrypto.EVP_CipherFinal_ex(this.cipherCtx, bArr2, 0);
                if (EVP_CipherFinal_ex > length) {
                    throw new ShortBufferWithoutStackTraceException(b.c("buffer is too short: ", EVP_CipherFinal_ex, " > ", length));
                }
                if (EVP_CipherFinal_ex > 0) {
                    System.arraycopy(bArr2, 0, bArr, i, EVP_CipherFinal_ex);
                }
                i3 = EVP_CipherFinal_ex;
            }
            reset();
            return (i3 + i) - i;
        }
        return 0;
    }

    @Override
    public void engineInitInternal(byte[] bArr, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        byte[] iv = algorithmParameterSpec instanceof IvParameterSpec ? ((IvParameterSpec) algorithmParameterSpec).getIV() : null;
        long EVP_get_cipherbyname = NativeCrypto.EVP_get_cipherbyname(getCipherName(bArr.length, this.mode));
        if (EVP_get_cipherbyname == 0) {
            StringBuilder d = androidx.base.a.b.d("Cannot find name for key length = ");
            d.append(bArr.length * 8);
            d.append(" and mode = ");
            d.append(this.mode);
            throw new InvalidAlgorithmParameterException(d.toString());
        }
        boolean isEncrypting = isEncrypting();
        int EVP_CIPHER_iv_length = NativeCrypto.EVP_CIPHER_iv_length(EVP_get_cipherbyname);
        if (iv != null || EVP_CIPHER_iv_length == 0) {
            if (EVP_CIPHER_iv_length == 0 && iv != null) {
                StringBuilder d2 = androidx.base.a.b.d("IV not used in ");
                d2.append(this.mode);
                d2.append(" mode");
                throw new InvalidAlgorithmParameterException(d2.toString());
            } else if (iv != null && iv.length != EVP_CIPHER_iv_length) {
                StringBuilder n = a.n("expected IV length of ", EVP_CIPHER_iv_length, " but was ");
                n.append(iv.length);
                throw new InvalidAlgorithmParameterException(n.toString());
            }
        } else if (!isEncrypting) {
            StringBuilder d3 = androidx.base.a.b.d("IV must be specified in ");
            d3.append(this.mode);
            d3.append(" mode");
            throw new InvalidAlgorithmParameterException(d3.toString());
        } else {
            iv = new byte[EVP_CIPHER_iv_length];
            if (secureRandom != null) {
                secureRandom.nextBytes(iv);
            } else {
                NativeCrypto.RAND_bytes(iv);
            }
        }
        this.iv = iv;
        boolean supportsVariableSizeKey = supportsVariableSizeKey();
        NativeRef.EVP_CIPHER_CTX evp_cipher_ctx = this.cipherCtx;
        if (supportsVariableSizeKey) {
            NativeCrypto.EVP_CipherInit_ex(evp_cipher_ctx, EVP_get_cipherbyname, null, null, isEncrypting);
            NativeCrypto.EVP_CIPHER_CTX_set_key_length(this.cipherCtx, bArr.length);
            NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, 0L, bArr, iv, isEncrypting());
        } else {
            NativeCrypto.EVP_CipherInit_ex(evp_cipher_ctx, EVP_get_cipherbyname, bArr, iv, isEncrypting);
        }
        NativeCrypto.EVP_CIPHER_CTX_set_padding(this.cipherCtx, getPadding() == OpenSSLCipher.Padding.PKCS5PADDING);
        this.modeBlockSize = NativeCrypto.EVP_CIPHER_CTX_block_size(this.cipherCtx);
        this.calledUpdate = false;
    }

    public abstract String getCipherName(int i, OpenSSLCipher.Mode mode);

    @Override
    public int getOutputSizeForFinal(int i) {
        if (this.modeBlockSize == 1) {
            return i;
        }
        int i2 = NativeCrypto.get_EVP_CIPHER_CTX_buf_len(this.cipherCtx);
        if (getPadding() == OpenSSLCipher.Padding.NOPADDING) {
            return i2 + i;
        }
        int i3 = i + i2;
        int i4 = 0;
        int i5 = i3 + (NativeCrypto.get_EVP_CIPHER_CTX_final_used(this.cipherCtx) ? this.modeBlockSize : 0);
        if (i5 % this.modeBlockSize != 0 || isEncrypting()) {
            i4 = this.modeBlockSize;
        }
        int i6 = i5 + i4;
        return i6 - (i6 % this.modeBlockSize);
    }

    @Override
    public int getOutputSizeForUpdate(int i) {
        return getOutputSizeForFinal(i);
    }

    @Override
    public int updateInternal(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        int length = bArr2.length - i3;
        if (length >= i4) {
            this.calledUpdate = true;
            return (NativeCrypto.EVP_CipherUpdate(this.cipherCtx, bArr2, i3, bArr, i, i2) + i3) - i3;
        }
        throw new ShortBufferWithoutStackTraceException(b.c("output buffer too small during update: ", length, " < ", i4));
    }
}
