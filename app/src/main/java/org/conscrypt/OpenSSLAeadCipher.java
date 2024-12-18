package org.conscrypt;

import androidx.base.a.a;
import androidx.base.a.b;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import org.conscrypt.OpenSSLCipher;
public abstract class OpenSSLAeadCipher extends OpenSSLCipher {
    public static final int DEFAULT_TAG_SIZE_BITS = 128;
    private static final boolean ENABLE_BYTEBUFFER_OPTIMIZATIONS = true;
    private static int lastGlobalMessageSize = 32;
    private byte[] aad;
    public byte[] buf;
    public int bufCount;
    public long evpAead;
    private boolean mustInitialize;
    private byte[] previousIv;
    private byte[] previousKey;
    public int tagLengthInBytes;

    public OpenSSLAeadCipher(OpenSSLCipher.Mode mode) {
        super(mode, OpenSSLCipher.Padding.NOPADDING);
    }

    private boolean arraysAreEqual(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return false;
        }
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i |= bArr[i2] ^ bArr2[i2];
        }
        return i == 0;
    }

    private void checkInitialization() {
        if (this.mustInitialize) {
            throw new IllegalStateException("Cannot re-use same key and IV for multiple encryptions");
        }
    }

    private void expand(int i) {
        int i2 = this.bufCount;
        int i3 = i2 + i;
        byte[] bArr = this.buf;
        if (i3 <= bArr.length) {
            return;
        }
        byte[] bArr2 = new byte[(i + i2) * 2];
        System.arraycopy(bArr, 0, bArr2, 0, i2);
        this.buf = bArr2;
    }

    private void reset() {
        this.aad = null;
        int i = lastGlobalMessageSize;
        byte[] bArr = this.buf;
        if (bArr == null) {
            this.buf = new byte[i];
        } else {
            int i2 = this.bufCount;
            if (i2 > 0 && i2 != i) {
                lastGlobalMessageSize = i2;
                if (bArr.length != i2) {
                    this.buf = new byte[i2];
                }
            }
        }
        this.bufCount = 0;
    }

    private void throwAEADBadTagExceptionIfAvailable(java.lang.String r6, java.lang.Throwable r7) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: org.conscrypt.OpenSSLAeadCipher.throwAEADBadTagExceptionIfAvailable(java.lang.String, java.lang.Throwable):void");
    }

    public boolean allowsNonceReuse() {
        return false;
    }

    @Override
    public void checkSupportedPadding(OpenSSLCipher.Padding padding) {
        if (padding != OpenSSLCipher.Padding.NOPADDING) {
            throw new NoSuchPaddingException("Must be NoPadding for AEAD ciphers");
        }
    }

    public void checkSupportedTagLength(int i) {
        if (i % 8 != 0) {
            throw new InvalidAlgorithmParameterException(a.h("Tag length must be a multiple of 8; was ", i));
        }
    }

    public int doFinalInternal(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        checkInitialization();
        try {
            int EVP_AEAD_CTX_seal_buf = isEncrypting() ? NativeCrypto.EVP_AEAD_CTX_seal_buf(this.evpAead, this.encodedKey, this.tagLengthInBytes, byteBuffer2, this.iv, byteBuffer, this.aad) : NativeCrypto.EVP_AEAD_CTX_open_buf(this.evpAead, this.encodedKey, this.tagLengthInBytes, byteBuffer2, this.iv, byteBuffer, this.aad);
            if (isEncrypting()) {
                this.mustInitialize = true;
            }
            return EVP_AEAD_CTX_seal_buf;
        } catch (BadPaddingException e) {
            throwAEADBadTagExceptionIfAvailable(e.getMessage(), e.getCause());
            throw e;
        }
    }

    @Override
    public int doFinalInternal(byte[] bArr, int i, int i2) {
        checkInitialization();
        try {
            int EVP_AEAD_CTX_seal = isEncrypting() ? NativeCrypto.EVP_AEAD_CTX_seal(this.evpAead, this.encodedKey, this.tagLengthInBytes, bArr, i, this.iv, this.buf, 0, this.bufCount, this.aad) : NativeCrypto.EVP_AEAD_CTX_open(this.evpAead, this.encodedKey, this.tagLengthInBytes, bArr, i, this.iv, this.buf, 0, this.bufCount, this.aad);
            if (isEncrypting()) {
                this.mustInitialize = true;
            }
            reset();
            return EVP_AEAD_CTX_seal;
        } catch (BadPaddingException e) {
            throwAEADBadTagExceptionIfAvailable(e.getMessage(), e.getCause());
            throw e;
        }
    }

    @Override
    public int engineDoFinal(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        int doFinalInternal;
        if (byteBuffer == null || byteBuffer2 == null) {
            throw new NullPointerException("Null ByteBuffer Error");
        }
        if (getOutputSizeForFinal(byteBuffer.remaining()) <= byteBuffer2.remaining()) {
            if (byteBuffer2.isReadOnly()) {
                throw new IllegalArgumentException("Cannot write to Read Only ByteBuffer");
            }
            if (this.bufCount != 0) {
                return super.engineDoFinal(byteBuffer, byteBuffer2);
            }
            if (!byteBuffer.isDirect()) {
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(byteBuffer.remaining());
                allocateDirect.mark();
                allocateDirect.put(byteBuffer);
                allocateDirect.reset();
                byteBuffer = allocateDirect;
            }
            if (byteBuffer2.isDirect()) {
                doFinalInternal = doFinalInternal(byteBuffer, byteBuffer2);
                byteBuffer2.position(byteBuffer2.position() + doFinalInternal);
            } else {
                ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(getOutputSizeForFinal(byteBuffer.remaining()));
                doFinalInternal = doFinalInternal(byteBuffer, allocateDirect2);
                byteBuffer2.put(allocateDirect2);
            }
            byteBuffer.position(byteBuffer.limit());
            return doFinalInternal;
        }
        throw new ShortBufferWithoutStackTraceException("Insufficient Bytes for Output Buffer");
    }

    @Override
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (bArr2 == null || getOutputSizeForFinal(i2) <= bArr2.length - i3) {
            return super.engineDoFinal(bArr, i, i2, bArr2, i3);
        }
        throw new ShortBufferWithoutStackTraceException("Insufficient output space");
    }

    @Override
    public void engineInitInternal(byte[] bArr, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        byte[] bArr2 = null;
        int i = 128;
        if (algorithmParameterSpec != null) {
            GCMParameters fromGCMParameterSpec = Platform.fromGCMParameterSpec(algorithmParameterSpec);
            if (fromGCMParameterSpec != null) {
                bArr2 = fromGCMParameterSpec.getIV();
                i = fromGCMParameterSpec.getTLen();
            } else if (algorithmParameterSpec instanceof IvParameterSpec) {
                bArr2 = ((IvParameterSpec) algorithmParameterSpec).getIV();
            }
        }
        checkSupportedTagLength(i);
        this.tagLengthInBytes = i / 8;
        boolean isEncrypting = isEncrypting();
        long evp_aead = getEVP_AEAD(bArr.length);
        this.evpAead = evp_aead;
        int EVP_AEAD_nonce_length = NativeCrypto.EVP_AEAD_nonce_length(evp_aead);
        if (bArr2 != null || EVP_AEAD_nonce_length == 0) {
            if (EVP_AEAD_nonce_length == 0 && bArr2 != null) {
                StringBuilder d = b.d("IV not used in ");
                d.append(this.mode);
                d.append(" mode");
                throw new InvalidAlgorithmParameterException(d.toString());
            } else if (bArr2 != null && bArr2.length != EVP_AEAD_nonce_length) {
                StringBuilder n = a.n("Expected IV length of ", EVP_AEAD_nonce_length, " but was ");
                n.append(bArr2.length);
                throw new InvalidAlgorithmParameterException(n.toString());
            }
        } else if (!isEncrypting) {
            StringBuilder d2 = b.d("IV must be specified in ");
            d2.append(this.mode);
            d2.append(" mode");
            throw new InvalidAlgorithmParameterException(d2.toString());
        } else {
            bArr2 = new byte[EVP_AEAD_nonce_length];
            if (secureRandom != null) {
                secureRandom.nextBytes(bArr2);
            } else {
                NativeCrypto.RAND_bytes(bArr2);
            }
        }
        if (isEncrypting() && bArr2 != null && !allowsNonceReuse()) {
            byte[] bArr3 = this.previousKey;
            if (bArr3 != null && this.previousIv != null && arraysAreEqual(bArr3, bArr) && arraysAreEqual(this.previousIv, bArr2)) {
                this.mustInitialize = true;
                throw new InvalidAlgorithmParameterException("When using AEAD key and IV must not be re-used");
            } else {
                this.previousKey = bArr;
                this.previousIv = bArr2;
            }
        }
        this.mustInitialize = false;
        this.iv = bArr2;
        reset();
    }

    @Override
    public void engineUpdateAAD(ByteBuffer byteBuffer) {
        checkInitialization();
        byte[] bArr = this.aad;
        if (bArr == null) {
            byte[] bArr2 = new byte[byteBuffer.remaining()];
            this.aad = bArr2;
            byteBuffer.get(bArr2);
            return;
        }
        byte[] bArr3 = new byte[byteBuffer.remaining() + bArr.length];
        byte[] bArr4 = this.aad;
        System.arraycopy(bArr4, 0, bArr3, 0, bArr4.length);
        byteBuffer.get(bArr3, this.aad.length, byteBuffer.remaining());
        this.aad = bArr3;
    }

    @Override
    public void engineUpdateAAD(byte[] bArr, int i, int i2) {
        checkInitialization();
        byte[] bArr2 = this.aad;
        if (bArr2 == null) {
            this.aad = Arrays.copyOfRange(bArr, i, i2 + i);
            return;
        }
        byte[] bArr3 = new byte[bArr2.length + i2];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, i, bArr3, this.aad.length, i2);
        this.aad = bArr3;
    }

    public abstract long getEVP_AEAD(int i);

    @Override
    public int getOutputSizeForFinal(int i) {
        return this.bufCount + i + (isEncrypting() ? NativeCrypto.EVP_AEAD_max_overhead(this.evpAead) : 0);
    }

    @Override
    public int getOutputSizeForUpdate(int i) {
        return 0;
    }

    @Override
    public int updateInternal(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        checkInitialization();
        if (this.buf != null) {
            ArrayUtils.checkOffsetAndCount(bArr.length, i, i2);
            if (i2 > 0) {
                expand(i2);
                System.arraycopy(bArr, i, this.buf, this.bufCount, i2);
                this.bufCount += i2;
                return 0;
            }
            return 0;
        }
        throw new IllegalStateException("Cipher not initialized");
    }
}
