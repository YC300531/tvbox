package org.conscrypt;

import androidx.base.a.b;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
public class DESEDESecretKeyFactory extends SecretKeyFactorySpi {
    @Override
    public SecretKey engineGenerateSecret(KeySpec keySpec) {
        if (keySpec != null) {
            if (!(keySpec instanceof SecretKeySpec)) {
                if (keySpec instanceof DESedeKeySpec) {
                    return new SecretKeySpec(((DESedeKeySpec) keySpec).getKey(), "DESEDE");
                }
                StringBuilder d = b.d("Unsupported KeySpec class: ");
                d.append(keySpec.getClass().getName());
                throw new InvalidKeySpecException(d.toString());
            }
            SecretKeySpec secretKeySpec = (SecretKeySpec) keySpec;
            try {
                if (DESedeKeySpec.isParityAdjusted(secretKeySpec.getEncoded(), 0)) {
                    return secretKeySpec;
                }
                throw new InvalidKeySpecException("SecretKeySpec is not a parity-adjusted DESEDE key");
            } catch (InvalidKeyException e) {
                throw new InvalidKeySpecException(e);
            }
        }
        throw new InvalidKeySpecException("Null KeySpec");
    }

    @Override
    public KeySpec engineGetKeySpec(SecretKey secretKey, Class cls) {
        if (secretKey != null) {
            if (cls == SecretKeySpec.class) {
                try {
                    if (DESedeKeySpec.isParityAdjusted(secretKey.getEncoded(), 0)) {
                        return secretKey instanceof SecretKeySpec ? (KeySpec) secretKey : new SecretKeySpec(secretKey.getEncoded(), "DESEDE");
                    }
                    throw new InvalidKeySpecException("SecretKey is not a parity-adjusted DESEDE key");
                } catch (InvalidKeyException e) {
                    throw new InvalidKeySpecException(e);
                }
            } else if (cls == DESedeKeySpec.class) {
                try {
                    return new DESedeKeySpec(secretKey.getEncoded());
                } catch (InvalidKeyException e2) {
                    throw new InvalidKeySpecException(e2);
                }
            } else {
                throw new InvalidKeySpecException(androidx.base.r5.b.f("Unsupported KeySpec class: ", cls));
            }
        }
        throw new InvalidKeySpecException("Null SecretKey");
    }

    @Override
    public SecretKey engineTranslateKey(SecretKey secretKey) {
        if (secretKey != null) {
            return new SecretKeySpec(secretKey.getEncoded(), secretKey.getAlgorithm());
        }
        throw new InvalidKeyException("Null SecretKey");
    }
}
