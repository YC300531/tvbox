package org.conscrypt;

import androidx.base.a.b;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
public class IvParameters extends AlgorithmParametersSpi {
    private byte[] iv;

    public static class AES extends IvParameters {
    }

    public static class ChaCha20 extends IvParameters {
    }

    public static class DESEDE extends IvParameters {
    }

    @Override
    public byte[] engineGetEncoded() {
        long j = 0;
        try {
            try {
                j = NativeCrypto.asn1_write_init();
                NativeCrypto.asn1_write_octetstring(j, this.iv);
                return NativeCrypto.asn1_write_finish(j);
            } catch (IOException e) {
                NativeCrypto.asn1_write_cleanup(j);
                throw e;
            }
        } finally {
            NativeCrypto.asn1_write_free(j);
        }
    }

    @Override
    public byte[] engineGetEncoded(String str) {
        if (str == null || str.equals("ASN.1")) {
            return engineGetEncoded();
        }
        if (str.equals("RAW")) {
            return (byte[]) this.iv.clone();
        }
        throw new IOException(b.b("Unsupported format: ", str));
    }

    @Override
    public <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> cls) {
        if (cls == IvParameterSpec.class) {
            return new IvParameterSpec(this.iv);
        }
        throw new InvalidParameterSpecException(androidx.base.r5.b.f("Incompatible AlgorithmParametersSpec class: ", cls));
    }

    @Override
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
            throw new InvalidParameterSpecException("Only IvParameterSpec is supported");
        }
        this.iv = (byte[]) ((IvParameterSpec) algorithmParameterSpec).getIV().clone();
    }

    @Override
    public void engineInit(byte[] bArr) {
        long j;
        try {
            j = NativeCrypto.asn1_read_init(bArr);
            try {
                byte[] asn1_read_octetstring = NativeCrypto.asn1_read_octetstring(j);
                if (!NativeCrypto.asn1_read_is_empty(j)) {
                    throw new IOException("Error reading ASN.1 encoding");
                }
                this.iv = asn1_read_octetstring;
                NativeCrypto.asn1_read_free(j);
            } catch (Throwable th) {
                th = th;
                NativeCrypto.asn1_read_free(j);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            j = 0;
        }
    }

    @Override
    public void engineInit(byte[] bArr, String str) {
        if (str == null || str.equals("ASN.1")) {
            engineInit(bArr);
        } else if (!str.equals("RAW")) {
            throw new IOException(b.b("Unsupported format: ", str));
        } else {
            this.iv = (byte[]) bArr.clone();
        }
    }

    @Override
    public String engineToString() {
        return "Conscrypt IV AlgorithmParameters";
    }
}
