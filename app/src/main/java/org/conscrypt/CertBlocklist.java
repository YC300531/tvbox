package org.conscrypt;

import java.math.BigInteger;
import java.security.PublicKey;
public interface CertBlocklist {
    boolean isPublicKeyBlockListed(PublicKey publicKey);

    boolean isSerialNumberBlockListed(BigInteger bigInteger);
}
