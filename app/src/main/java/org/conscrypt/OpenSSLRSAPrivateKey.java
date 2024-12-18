package org.conscrypt;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Objects;
class OpenSSLRSAPrivateKey implements RSAPrivateKey, OpenSSLKeyHolder {
    private static final long serialVersionUID = 4872170254439578735L;
    public transient boolean fetchedParams;
    public transient OpenSSLKey key;
    public BigInteger modulus;
    public BigInteger privateExponent;

    public OpenSSLRSAPrivateKey(RSAPrivateKeySpec rSAPrivateKeySpec) {
        this(init(rSAPrivateKeySpec));
    }

    public OpenSSLRSAPrivateKey(OpenSSLKey openSSLKey) {
        this.key = openSSLKey;
    }

    public OpenSSLRSAPrivateKey(OpenSSLKey openSSLKey, byte[][] bArr) {
        this(openSSLKey);
        readParams(bArr);
        this.fetchedParams = true;
    }

    public static OpenSSLKey getInstance(RSAPrivateKey rSAPrivateKey) {
        if (rSAPrivateKey.getFormat() == null) {
            return wrapPlatformKey(rSAPrivateKey);
        }
        BigInteger modulus = rSAPrivateKey.getModulus();
        BigInteger privateExponent = rSAPrivateKey.getPrivateExponent();
        if (modulus != null) {
            if (privateExponent != null) {
                try {
                    return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(modulus.toByteArray(), null, privateExponent.toByteArray(), null, null, null, null, null));
                } catch (Exception e) {
                    throw new InvalidKeyException(e);
                }
            }
            throw new InvalidKeyException("privateExponent == null");
        }
        throw new InvalidKeyException("modulus == null");
    }

    public static OpenSSLRSAPrivateKey getInstance(OpenSSLKey openSSLKey) {
        byte[][] bArr = NativeCrypto.get_RSA_private_params(openSSLKey.getNativeRef());
        return bArr[1] != null ? new OpenSSLRSAPrivateCrtKey(openSSLKey, bArr) : new OpenSSLRSAPrivateKey(openSSLKey, bArr);
    }

    private static OpenSSLKey init(RSAPrivateKeySpec rSAPrivateKeySpec) {
        BigInteger modulus = rSAPrivateKeySpec.getModulus();
        BigInteger privateExponent = rSAPrivateKeySpec.getPrivateExponent();
        if (modulus != null) {
            if (privateExponent != null) {
                try {
                    return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(modulus.toByteArray(), null, privateExponent.toByteArray(), null, null, null, null, null));
                } catch (Exception e) {
                    throw new InvalidKeySpecException(e);
                }
            }
            throw new InvalidKeySpecException("privateExponent == null");
        }
        throw new InvalidKeySpecException("modulus == null");
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(this.modulus.toByteArray(), null, this.privateExponent.toByteArray(), null, null, null, null, null));
        this.fetchedParams = true;
    }

    public static org.conscrypt.OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(java.security.PrivateKey r3, java.security.PublicKey r4) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: org.conscrypt.OpenSSLRSAPrivateKey.wrapJCAPrivateKeyForTLSStackOnly(java.security.PrivateKey, java.security.PublicKey):org.conscrypt.OpenSSLKey");
    }

    public static OpenSSLKey wrapPlatformKey(RSAPrivateKey rSAPrivateKey) {
        OpenSSLKey wrapRsaKey = Platform.wrapRsaKey(rSAPrivateKey);
        return wrapRsaKey != null ? wrapRsaKey : new OpenSSLKey(NativeCrypto.getRSAPrivateKeyWrapper(rSAPrivateKey, rSAPrivateKey.getModulus().toByteArray()), true);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        if (this.key.isHardwareBacked()) {
            throw new NotSerializableException("Hardware backed keys can not be serialized");
        }
        ensureReadParams();
        objectOutputStream.defaultWriteObject();
    }

    public final synchronized void ensureReadParams() {
        if (this.fetchedParams) {
            return;
        }
        readParams(NativeCrypto.get_RSA_private_params(this.key.getNativeRef()));
        this.fetchedParams = true;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof OpenSSLRSAPrivateKey) {
            return this.key.equals(((OpenSSLRSAPrivateKey) obj).getOpenSSLKey());
        }
        if (obj instanceof RSAPrivateKey) {
            ensureReadParams();
            RSAPrivateKey rSAPrivateKey = (RSAPrivateKey) obj;
            return this.modulus.equals(rSAPrivateKey.getModulus()) && this.privateExponent.equals(rSAPrivateKey.getPrivateExponent());
        }
        return false;
    }

    @Override
    public final String getAlgorithm() {
        return "RSA";
    }

    @Override
    public final byte[] getEncoded() {
        if (this.key.isHardwareBacked()) {
            return null;
        }
        return NativeCrypto.EVP_marshal_private_key(this.key.getNativeRef());
    }

    @Override
    public final String getFormat() {
        if (this.key.isHardwareBacked()) {
            return null;
        }
        return "PKCS#8";
    }

    @Override
    public final BigInteger getModulus() {
        ensureReadParams();
        return this.modulus;
    }

    @Override
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    @Override
    public final BigInteger getPrivateExponent() {
        if (this.key.isHardwareBacked()) {
            
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Private exponent cannot be extracted");
        }
        ensureReadParams();
        return this.privateExponent;
    }

    public int hashCode() {
        ensureReadParams();
        int hashCode = this.modulus.hashCode() + 3;
        BigInteger bigInteger = this.privateExponent;
        return bigInteger != null ? (hashCode * 7) + bigInteger.hashCode() : hashCode;
    }

    public void readParams(byte[][] bArr) {
        Objects.requireNonNull(bArr[0], "modulus == null");
        if (bArr[2] == null && !this.key.isHardwareBacked()) {
            throw new NullPointerException("privateExponent == null");
        }
        this.modulus = new BigInteger(bArr[0]);
        if (bArr[2] != null) {
            this.privateExponent = new BigInteger(bArr[2]);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("OpenSSLRSAPrivateKey{");
        ensureReadParams();
        sb.append("modulus=");
        sb.append(this.modulus.toString(16));
        return sb.toString();
    }
}
