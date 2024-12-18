package org.conscrypt;

import androidx.base.a.b;
import java.security.PublicKey;
import java.util.Arrays;
final class X509PublicKey implements PublicKey {
    private static final long serialVersionUID = -8610156854731664298L;
    private final String algorithm;
    private final byte[] encoded;

    public X509PublicKey(String str, byte[] bArr) {
        this.algorithm = str;
        this.encoded = bArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && X509PublicKey.class == obj.getClass()) {
            X509PublicKey x509PublicKey = (X509PublicKey) obj;
            String str = this.algorithm;
            if (str == null) {
                if (x509PublicKey.algorithm != null) {
                    return false;
                }
            } else if (!str.equals(x509PublicKey.algorithm)) {
                return false;
            }
            return Arrays.equals(this.encoded, x509PublicKey.encoded);
        }
        return false;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public byte[] getEncoded() {
        return this.encoded;
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    public int hashCode() {
        String str = this.algorithm;
        return Arrays.hashCode(this.encoded) + (((str == null ? 0 : str.hashCode()) + 31) * 31);
    }

    public String toString() {
        StringBuilder d = b.d("X509PublicKey [algorithm=");
        d.append(this.algorithm);
        d.append(", encoded=");
        d.append(Arrays.toString(this.encoded));
        d.append("]");
        return d.toString();
    }
}
