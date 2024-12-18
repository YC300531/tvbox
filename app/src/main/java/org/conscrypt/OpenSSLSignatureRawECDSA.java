package org.conscrypt;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
public class OpenSSLSignatureRawECDSA extends SignatureSpi {
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private OpenSSLKey key;

    private static OpenSSLKey verifyKey(OpenSSLKey openSSLKey) {
        if (NativeCrypto.EVP_PKEY_type(openSSLKey.getNativeRef()) == 408) {
            return openSSLKey;
        }
        throw new InvalidKeyException("Non-EC key used to initialize EC signature.");
    }

    @Override
    public Object engineGetParameter(String str) {
        return null;
    }

    @Override
    public void engineInitSign(PrivateKey privateKey) {
        this.key = verifyKey(OpenSSLKey.fromPrivateKey(privateKey));
    }

    @Override
    public void engineInitVerify(PublicKey publicKey) {
        this.key = verifyKey(OpenSSLKey.fromPublicKey(publicKey));
    }

    @Override
    public void engineSetParameter(String str, Object obj) {
    }

    @Override
    public byte[] engineSign() {
        OpenSSLKey openSSLKey = this.key;
        if (openSSLKey != null) {
            int ECDSA_size = NativeCrypto.ECDSA_size(openSSLKey.getNativeRef());
            byte[] bArr = new byte[ECDSA_size];
            try {
                try {
                    int ECDSA_sign = NativeCrypto.ECDSA_sign(this.buffer.toByteArray(), bArr, this.key.getNativeRef());
                    if (ECDSA_sign >= 0) {
                        if (ECDSA_sign != ECDSA_size) {
                            byte[] bArr2 = new byte[ECDSA_sign];
                            System.arraycopy(bArr, 0, bArr2, 0, ECDSA_sign);
                            bArr = bArr2;
                        }
                        return bArr;
                    }
                    throw new SignatureException("Could not compute signature.");
                } catch (Exception e) {
                    throw new SignatureException(e);
                }
            } finally {
                this.buffer.reset();
            }
        }
        throw new SignatureException("No key provided");
    }

    @Override
    public void engineUpdate(byte b) {
        this.buffer.write(b);
    }

    @Override
    public void engineUpdate(byte[] bArr, int i, int i2) {
        this.buffer.write(bArr, i, i2);
    }

    @Override
    public boolean engineVerify(byte[] bArr) {
        if (this.key != null) {
            try {
                try {
                    int ECDSA_verify = NativeCrypto.ECDSA_verify(this.buffer.toByteArray(), bArr, this.key.getNativeRef());
                    if (ECDSA_verify != -1) {
                        return ECDSA_verify == 1;
                    }
                    throw new SignatureException("Could not verify signature.");
                } catch (Exception e) {
                    throw new SignatureException(e);
                }
            } finally {
                this.buffer.reset();
            }
        }
        throw new SignatureException("No key provided");
    }
}
