package org.conscrypt;

import androidx.base.a.a;
import androidx.base.a.b;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.conscrypt.NativeRef;
public final class OpenSSLECDHKeyAgreement extends KeyAgreementSpi {
    private int mExpectedResultLength;
    private OpenSSLKey mOpenSslPrivateKey;
    private byte[] mResult;

    private void checkCompleted() {
        if (this.mResult == null) {
            throw new IllegalStateException("Key agreement not completed");
        }
    }

    @Override
    public Key engineDoPhase(Key key, boolean z) {
        if (this.mOpenSslPrivateKey != null) {
            if (z) {
                if (key != null) {
                    if (!(key instanceof PublicKey)) {
                        StringBuilder d = b.d("Not a public key: ");
                        d.append(key.getClass());
                        throw new InvalidKeyException(d.toString());
                    }
                    OpenSSLKey fromPublicKey = OpenSSLKey.fromPublicKey((PublicKey) key);
                    byte[] bArr = new byte[this.mExpectedResultLength];
                    int ECDH_compute_key = NativeCrypto.ECDH_compute_key(bArr, 0, fromPublicKey.getNativeRef(), this.mOpenSslPrivateKey.getNativeRef());
                    if (ECDH_compute_key != -1) {
                        int i = this.mExpectedResultLength;
                        if (ECDH_compute_key != i) {
                            if (ECDH_compute_key >= i) {
                                StringBuilder d2 = b.d("Engine produced a longer than expected result. Expected: ");
                                d2.append(this.mExpectedResultLength);
                                d2.append(", actual: ");
                                d2.append(ECDH_compute_key);
                                throw new RuntimeException(d2.toString());
                            }
                            byte[] bArr2 = this.mResult;
                            System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
                            bArr = new byte[ECDH_compute_key];
                        }
                        this.mResult = bArr;
                        return null;
                    }
                    throw new RuntimeException(a.h("Engine returned ", ECDH_compute_key));
                }
                throw new InvalidKeyException("key == null");
            }
            throw new IllegalStateException("ECDH only has one phase");
        }
        throw new IllegalStateException("Not initialized");
    }

    @Override
    public int engineGenerateSecret(byte[] bArr, int i) {
        checkCompleted();
        int length = bArr.length - i;
        byte[] bArr2 = this.mResult;
        if (bArr2.length <= length) {
            System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
            return this.mResult.length;
        }
        StringBuilder d = b.d("Needed: ");
        d.append(this.mResult.length);
        d.append(", available: ");
        d.append(length);
        throw new ShortBufferWithoutStackTraceException(d.toString());
    }

    @Override
    public SecretKey engineGenerateSecret(String str) {
        checkCompleted();
        return new SecretKeySpec(engineGenerateSecret(), str);
    }

    @Override
    public byte[] engineGenerateSecret() {
        checkCompleted();
        return this.mResult;
    }

    @Override
    public void engineInit(Key key, SecureRandom secureRandom) {
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if (!(key instanceof PrivateKey)) {
            StringBuilder d = b.d("Not a private key: ");
            d.append(key.getClass());
            throw new InvalidKeyException(d.toString());
        }
        OpenSSLKey fromPrivateKey = OpenSSLKey.fromPrivateKey((PrivateKey) key);
        this.mExpectedResultLength = (NativeCrypto.EC_GROUP_get_degree(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(fromPrivateKey.getNativeRef()))) + 7) / 8;
        this.mOpenSslPrivateKey = fromPrivateKey;
    }

    @Override
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        if (algorithmParameterSpec != null) {
            throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
        }
        engineInit(key, secureRandom);
    }
}
