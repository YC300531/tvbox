package org.conscrypt;

import java.nio.ByteBuffer;
import java.security.MessageDigestSpi;
import org.conscrypt.EvpMdRef;
import org.conscrypt.NativeRef;
public class OpenSSLMessageDigestJDK extends MessageDigestSpi implements Cloneable {
    private final NativeRef.EVP_MD_CTX ctx;
    private boolean digestInitializedInContext;
    private final long evp_md;
    private final byte[] singleByte;
    private final int size;

    public static class fun1 {
    }

    public static final class MD5 extends OpenSSLMessageDigestJDK {
        public MD5() {
            super(EvpMdRef.MD5.EVP_MD, EvpMdRef.MD5.SIZE_BYTES, null);
        }
    }

    public static final class SHA1 extends OpenSSLMessageDigestJDK {
        public SHA1() {
            super(EvpMdRef.SHA1.EVP_MD, EvpMdRef.SHA1.SIZE_BYTES, null);
        }
    }

    public static final class SHA224 extends OpenSSLMessageDigestJDK {
        public SHA224() {
            super(EvpMdRef.SHA224.EVP_MD, EvpMdRef.SHA224.SIZE_BYTES, null);
        }
    }

    public static final class SHA256 extends OpenSSLMessageDigestJDK {
        public SHA256() {
            super(EvpMdRef.SHA256.EVP_MD, EvpMdRef.SHA256.SIZE_BYTES, null);
        }
    }

    public static final class SHA384 extends OpenSSLMessageDigestJDK {
        public SHA384() {
            super(EvpMdRef.SHA384.EVP_MD, EvpMdRef.SHA384.SIZE_BYTES, null);
        }
    }

    public static final class SHA512 extends OpenSSLMessageDigestJDK {
        public SHA512() {
            super(EvpMdRef.SHA512.EVP_MD, EvpMdRef.SHA512.SIZE_BYTES, null);
        }
    }

    private OpenSSLMessageDigestJDK(long j, int i) {
        this.singleByte = new byte[1];
        this.evp_md = j;
        this.size = i;
        this.ctx = new NativeRef.EVP_MD_CTX(NativeCrypto.EVP_MD_CTX_create());
    }

    private OpenSSLMessageDigestJDK(long j, int i, NativeRef.EVP_MD_CTX evp_md_ctx, boolean z) {
        this.singleByte = new byte[1];
        this.evp_md = j;
        this.size = i;
        this.ctx = evp_md_ctx;
        this.digestInitializedInContext = z;
    }

    public OpenSSLMessageDigestJDK(long j, int i, 1 r4) {
        this(j, i);
    }

    private synchronized void ensureDigestInitializedInContext() {
        if (!this.digestInitializedInContext) {
            NativeCrypto.EVP_DigestInit_ex(this.ctx, this.evp_md);
            this.digestInitializedInContext = true;
        }
    }

    @Override
    public Object clone() {
        NativeRef.EVP_MD_CTX evp_md_ctx = new NativeRef.EVP_MD_CTX(NativeCrypto.EVP_MD_CTX_create());
        if (this.digestInitializedInContext) {
            NativeCrypto.EVP_MD_CTX_copy_ex(evp_md_ctx, this.ctx);
        }
        return new OpenSSLMessageDigestJDK(this.evp_md, this.size, evp_md_ctx, this.digestInitializedInContext);
    }

    @Override
    public synchronized byte[] engineDigest() {
        byte[] bArr;
        ensureDigestInitializedInContext();
        bArr = new byte[this.size];
        NativeCrypto.EVP_DigestFinal_ex(this.ctx, bArr, 0);
        this.digestInitializedInContext = false;
        return bArr;
    }

    @Override
    public int engineGetDigestLength() {
        return this.size;
    }

    @Override
    public synchronized void engineReset() {
        NativeCrypto.EVP_MD_CTX_cleanup(this.ctx);
        this.digestInitializedInContext = false;
    }

    @Override
    public synchronized void engineUpdate(byte b) {
        byte[] bArr = this.singleByte;
        bArr[0] = b;
        engineUpdate(bArr, 0, 1);
    }

    @Override
    public synchronized void engineUpdate(ByteBuffer byteBuffer) {
        if (byteBuffer.hasRemaining()) {
            if (!byteBuffer.isDirect()) {
                super.engineUpdate(byteBuffer);
                return;
            }
            long directBufferAddress = NativeCrypto.getDirectBufferAddress(byteBuffer);
            if (directBufferAddress == 0) {
                super.engineUpdate(byteBuffer);
                return;
            }
            int position = byteBuffer.position();
            if (position < 0) {
                throw new RuntimeException("Negative position");
            }
            long j = directBufferAddress + position;
            int remaining = byteBuffer.remaining();
            if (remaining < 0) {
                throw new RuntimeException("Negative remaining amount");
            }
            ensureDigestInitializedInContext();
            NativeCrypto.EVP_DigestUpdateDirect(this.ctx, j, remaining);
            byteBuffer.position(position + remaining);
        }
    }

    @Override
    public synchronized void engineUpdate(byte[] bArr, int i, int i2) {
        ensureDigestInitializedInContext();
        NativeCrypto.EVP_DigestUpdate(this.ctx, bArr, i, i2);
    }
}
