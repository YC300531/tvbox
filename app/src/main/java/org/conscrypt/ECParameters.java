package org.conscrypt;

import androidx.base.a.b;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.conscrypt.NativeRef;
public class ECParameters extends AlgorithmParametersSpi {
    private OpenSSLECGroupContext curve;

    @Override
    public byte[] engineGetEncoded() {
        return NativeCrypto.EC_KEY_marshal_curve_name(this.curve.getNativeRef());
    }

    @Override
    public byte[] engineGetEncoded(String str) {
        if (str == null || str.equals("ASN.1")) {
            return engineGetEncoded();
        }
        throw new IOException(b.b("Unsupported format: ", str));
    }

    @Override
    public <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> cls) {
        if (cls == ECParameterSpec.class) {
            return this.curve.getECParameterSpec();
        }
        if (cls == ECGenParameterSpec.class) {
            return new ECGenParameterSpec(this.curve.getCurveName());
        }
        throw new InvalidParameterSpecException(androidx.base.r5.b.f("Unsupported class: ", cls));
    }

    @Override
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (algorithmParameterSpec instanceof ECGenParameterSpec) {
            String name = ((ECGenParameterSpec) algorithmParameterSpec).getName();
            OpenSSLECGroupContext curveByName = OpenSSLECGroupContext.getCurveByName(name);
            if (curveByName == null) {
                throw new InvalidParameterSpecException(b.b("Unknown EC curve name: ", name));
            }
            this.curve = curveByName;
        } else if (!(algorithmParameterSpec instanceof ECParameterSpec)) {
            throw new InvalidParameterSpecException("Only ECParameterSpec and ECGenParameterSpec are supported");
        } else {
            ECParameterSpec eCParameterSpec = (ECParameterSpec) algorithmParameterSpec;
            try {
                OpenSSLECGroupContext openSSLECGroupContext = OpenSSLECGroupContext.getInstance(eCParameterSpec);
                if (openSSLECGroupContext != null) {
                    this.curve = openSSLECGroupContext;
                    return;
                }
                throw new InvalidParameterSpecException("Unknown EC curve: " + eCParameterSpec);
            } catch (InvalidAlgorithmParameterException e) {
                throw new InvalidParameterSpecException(e.getMessage());
            }
        }
    }

    @Override
    public void engineInit(byte[] bArr) {
        long EC_KEY_parse_curve_name = NativeCrypto.EC_KEY_parse_curve_name(bArr);
        if (EC_KEY_parse_curve_name == 0) {
            throw new IOException("Error reading ASN.1 encoding");
        }
        this.curve = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(EC_KEY_parse_curve_name));
    }

    @Override
    public void engineInit(byte[] bArr, String str) {
        if (str != null && !str.equals("ASN.1")) {
            throw new IOException(b.b("Unsupported format: ", str));
        }
        engineInit(bArr);
    }

    @Override
    public String engineToString() {
        return "Conscrypt EC AlgorithmParameters";
    }
}
