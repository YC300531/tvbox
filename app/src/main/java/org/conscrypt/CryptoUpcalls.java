package org.conscrypt;

import androidx.base.a.b;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.logging.Logger;
final class CryptoUpcalls {
    private static final Logger logger = Logger.getLogger(CryptoUpcalls.class.getName());

    private CryptoUpcalls() {
    }

    public static byte[] ecSignDigestWithPrivateKey(PrivateKey privateKey, byte[] bArr) {
        if ("EC".equals(privateKey.getAlgorithm())) {
            return signDigestWithPrivateKey(privateKey, bArr, "NONEwithECDSA");
        }
        StringBuilder d = b.d("Unexpected key type: ");
        d.append(privateKey.toString());
        throw new RuntimeException(d.toString());
    }

    private static ArrayList<Provider> getExternalProviders(String str) {
        Provider[] providers;
        ArrayList<Provider> arrayList = new ArrayList<>(1);
        for (Provider provider : Security.getProviders(str)) {
            if (!Conscrypt.isConscrypt(provider)) {
                arrayList.add(provider);
            }
        }
        if (arrayList.isEmpty()) {
            logger.warning("Could not find external provider for algorithm: " + str);
        }
        return arrayList;
    }

    public static byte[] rsaDecryptWithPrivateKey(PrivateKey privateKey, int i, byte[] bArr) {
        return rsaOpWithPrivateKey(privateKey, i, 2, bArr);
    }

    private static byte[] rsaOpWithPrivateKey(java.security.PrivateKey r5, int r6, int r7, byte[] r8) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: org.conscrypt.CryptoUpcalls.rsaOpWithPrivateKey(java.security.PrivateKey, int, int, byte[]):byte[]");
    }

    public static byte[] rsaSignDigestWithPrivateKey(PrivateKey privateKey, int i, byte[] bArr) {
        return rsaOpWithPrivateKey(privateKey, i, 1, bArr);
    }

    private static byte[] signDigestWithPrivateKey(java.security.PrivateKey r5, byte[] r6, java.lang.String r7) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: org.conscrypt.CryptoUpcalls.signDigestWithPrivateKey(java.security.PrivateKey, byte[], java.lang.String):byte[]");
    }
}
