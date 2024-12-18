package org.conscrypt;

import androidx.base.a.a;
import androidx.base.a.b;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
public final class OpenSSLECKeyPairGenerator extends KeyPairGenerator {
    private static final String ALGORITHM = "EC";
    private static final int DEFAULT_KEY_SIZE = 256;
    private static final Map<Integer, String> SIZE_TO_CURVE_NAME;
    private OpenSSLECGroupContext group;

    static {
        HashMap hashMap = new HashMap();
        SIZE_TO_CURVE_NAME = hashMap;
        hashMap.put(224, "secp224r1");
        hashMap.put(256, "prime256v1");
        hashMap.put(384, "secp384r1");
        hashMap.put(521, "secp521r1");
    }

    public OpenSSLECKeyPairGenerator() {
        super(ALGORITHM);
    }

    public static void assertCurvesAreValid() {
        ArrayList arrayList = new ArrayList();
        for (String str : SIZE_TO_CURVE_NAME.values()) {
            if (OpenSSLECGroupContext.getCurveByName(str) == null) {
                arrayList.add(str);
            }
        }
        if (arrayList.size() <= 0) {
            return;
        }
        StringBuilder d = b.d("Invalid curve names: ");
        d.append(Arrays.toString(arrayList.toArray()));
        throw new AssertionError(d.toString());
    }

    @Override
    public KeyPair generateKeyPair() {
        if (this.group == null) {
            String str = SIZE_TO_CURVE_NAME.get(256);
            OpenSSLECGroupContext curveByName = OpenSSLECGroupContext.getCurveByName(str);
            this.group = curveByName;
            if (curveByName == null) {
                throw new RuntimeException(b.b("Curve not recognized: ", str));
            }
        }
        OpenSSLKey openSSLKey = new OpenSSLKey(NativeCrypto.EC_KEY_generate_key(this.group.getNativeRef()));
        return new KeyPair(new OpenSSLECPublicKey(this.group, openSSLKey), new OpenSSLECPrivateKey(this.group, openSSLKey));
    }

    @Override
    public void initialize(int i, SecureRandom secureRandom) {
        String str = SIZE_TO_CURVE_NAME.get(Integer.valueOf(i));
        if (str == null) {
            throw new InvalidParameterException(a.h("unknown key size ", i));
        }
        OpenSSLECGroupContext curveByName = OpenSSLECGroupContext.getCurveByName(str);
        if (curveByName == null) {
            throw new InvalidParameterException(b.b("unknown curve ", str));
        }
        this.group = curveByName;
    }

    @Override
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        if (algorithmParameterSpec instanceof ECParameterSpec) {
            this.group = OpenSSLECGroupContext.getInstance((ECParameterSpec) algorithmParameterSpec);
        } else if (!(algorithmParameterSpec instanceof ECGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("parameter must be ECParameterSpec or ECGenParameterSpec");
        } else {
            String name = ((ECGenParameterSpec) algorithmParameterSpec).getName();
            OpenSSLECGroupContext curveByName = OpenSSLECGroupContext.getCurveByName(name);
            if (curveByName == null) {
                throw new InvalidAlgorithmParameterException(b.b("unknown curve name: ", name));
            }
            this.group = curveByName;
        }
    }
}
