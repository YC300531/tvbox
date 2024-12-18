package org.conscrypt;

import androidx.base.a.b;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedTrustManager;
import org.conscrypt.ct.CTPolicy;
import org.conscrypt.ct.CTVerifier;
public final class TrustManagerImpl extends X509ExtendedTrustManager {
    private static ConscryptHostnameVerifier defaultHostnameVerifier;
    private final X509Certificate[] acceptedIssuers;
    private final CertBlocklist blocklist;
    private boolean ctEnabledOverride;
    private CTPolicy ctPolicy;
    private CTVerifier ctVerifier;
    private final Exception err;
    private final CertificateFactory factory;
    private ConscryptHostnameVerifier hostnameVerifier;
    private final TrustedCertificateIndex intermediateIndex;
    private CertPinManager pinManager;
    private final KeyStore rootKeyStore;
    private final TrustedCertificateIndex trustedCertificateIndex;
    private final ConscryptCertStore trustedCertificateStore;
    private final CertPathValidator validator;
    private static final Logger logger = Logger.getLogger(TrustManagerImpl.class.getName());
    private static final TrustAnchorComparator TRUST_ANCHOR_COMPARATOR = new TrustAnchorComparator(null);

    public static class fun1 {
    }

    public static class ExtendedKeyUsagePKIXCertPathChecker extends PKIXCertPathChecker {
        private static final String EKU_anyExtendedKeyUsage = "2.5.29.37.0";
        private static final String EKU_clientAuth = "1.3.6.1.5.5.7.3.2";
        private static final String EKU_msSGC = "1.3.6.1.4.1.311.10.3.3";
        private static final String EKU_nsSGC = "2.16.840.1.113730.4.1";
        private static final String EKU_serverAuth = "1.3.6.1.5.5.7.3.1";
        private final boolean clientAuth;
        private final X509Certificate leaf;
        private static final String EKU_OID = "2.5.29.37";
        private static final Set<String> SUPPORTED_EXTENSIONS = Collections.unmodifiableSet(new HashSet(Arrays.asList(EKU_OID)));

        private ExtendedKeyUsagePKIXCertPathChecker(boolean z, X509Certificate x509Certificate) {
            this.clientAuth = z;
            this.leaf = x509Certificate;
        }

        public ExtendedKeyUsagePKIXCertPathChecker(boolean z, X509Certificate x509Certificate, 1 r3) {
            this(z, x509Certificate);
        }

        @Override
        public void check(Certificate certificate, Collection<String> collection) {
            X509Certificate x509Certificate = this.leaf;
            if (certificate != x509Certificate) {
                return;
            }
            try {
                List<String> extendedKeyUsage = x509Certificate.getExtendedKeyUsage();
                if (extendedKeyUsage == null) {
                    return;
                }
                boolean z = false;
                for (String str : extendedKeyUsage) {
                    if (!str.equals(EKU_anyExtendedKeyUsage)) {
                        if (this.clientAuth) {
                            if (str.equals(EKU_clientAuth)) {
                            }
                        } else if (!str.equals(EKU_serverAuth) && !str.equals(EKU_nsSGC) && !str.equals(EKU_msSGC)) {
                        }
                    }
                    z = true;
                }
                if (!z) {
                    throw new CertPathValidatorException("End-entity certificate does not have a valid extendedKeyUsage.");
                }
                collection.remove(EKU_OID);
            } catch (CertificateParsingException e) {
                throw new CertPathValidatorException(e);
            }
        }

        @Override
        public Set<String> getSupportedExtensions() {
            return SUPPORTED_EXTENSIONS;
        }

        @Override
        public void init(boolean z) {
        }

        @Override
        public boolean isForwardCheckingSupported() {
            return true;
        }
    }

    public static class TrustAnchorComparator implements Comparator<TrustAnchor> {
        private static final CertificatePriorityComparator CERT_COMPARATOR = new CertificatePriorityComparator();

        private TrustAnchorComparator() {
        }

        public TrustAnchorComparator(1 r1) {
            this();
        }

        @Override
        public int compare(TrustAnchor trustAnchor, TrustAnchor trustAnchor2) {
            return CERT_COMPARATOR.compare(trustAnchor.getTrustedCert(), trustAnchor2.getTrustedCert());
        }
    }

    public TrustManagerImpl(KeyStore keyStore) {
        this(keyStore, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager certPinManager) {
        this(keyStore, certPinManager, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager certPinManager, ConscryptCertStore conscryptCertStore) {
        this(keyStore, certPinManager, conscryptCertStore, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager certPinManager, ConscryptCertStore conscryptCertStore, CertBlocklist certBlocklist) {
        this(keyStore, certPinManager, conscryptCertStore, certBlocklist, null, null, null);
    }

    public TrustManagerImpl(java.security.KeyStore r6, org.conscrypt.CertPinManager r7, org.conscrypt.ConscryptCertStore r8, org.conscrypt.CertBlocklist r9, org.conscrypt.ct.CTLogStore r10, org.conscrypt.ct.CTVerifier r11, org.conscrypt.ct.CTPolicy r12) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: org.conscrypt.TrustManagerImpl.<init>(java.security.KeyStore, org.conscrypt.CertPinManager, org.conscrypt.ConscryptCertStore, org.conscrypt.CertBlocklist, org.conscrypt.ct.CTLogStore, org.conscrypt.ct.CTVerifier, org.conscrypt.ct.CTPolicy):void");
    }

    private static X509Certificate[] acceptedIssuers(KeyStore keyStore) {
        try {
            ArrayList arrayList = new ArrayList();
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(aliases.nextElement());
                if (x509Certificate != null) {
                    arrayList.add(x509Certificate);
                }
            }
            return (X509Certificate[]) arrayList.toArray(new X509Certificate[arrayList.size()]);
        } catch (KeyStoreException unused) {
            return new X509Certificate[0];
        }
    }

    private void checkBlocklist(X509Certificate x509Certificate) {
        CertBlocklist certBlocklist = this.blocklist;
        if (certBlocklist == null || !certBlocklist.isPublicKeyBlockListed(x509Certificate.getPublicKey())) {
            return;
        }
        throw new CertificateException("Certificate blocklisted by public key: " + x509Certificate);
    }

    private void checkCT(String str, List<X509Certificate> list, byte[] bArr, byte[] bArr2) {
        if (!this.ctPolicy.doesResultConformToPolicy(this.ctVerifier.verifySignedCertificateTimestamps(list, bArr2, bArr), str, (X509Certificate[]) list.toArray(new X509Certificate[list.size()]))) {
            throw new CertificateException("Certificate chain does not conform to required transparency policy.");
        }
    }

    private List<X509Certificate> checkTrusted(X509Certificate[] x509CertificateArr, String str, SSLSession sSLSession, SSLParameters sSLParameters, boolean z) {
        byte[] bArr;
        byte[] bArr2;
        String str2;
        if (sSLSession != null) {
            str2 = sSLSession.getPeerHost();
            bArr = getOcspDataFromSession(sSLSession);
            bArr2 = getTlsSctDataFromSession(sSLSession);
        } else {
            bArr = null;
            bArr2 = null;
            str2 = null;
        }
        if (sSLSession == null || sSLParameters == null || !"HTTPS".equalsIgnoreCase(sSLParameters.getEndpointIdentificationAlgorithm()) || getHttpsVerifier().verify(x509CertificateArr, str2, sSLSession)) {
            return checkTrusted(x509CertificateArr, bArr, bArr2, str, str2, z);
        }
        throw new CertificateException("No subjectAltNames on the certificate match");
    }

    private List<X509Certificate> checkTrusted(X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2, String str, String str2, boolean z) {
        if (x509CertificateArr == null || x509CertificateArr.length == 0 || str == null || str.length() == 0) {
            throw new IllegalArgumentException("null or zero-length parameter");
        }
        if (this.err == null) {
            HashSet hashSet = new HashSet();
            ArrayList<X509Certificate> arrayList = new ArrayList<>();
            ArrayList<TrustAnchor> arrayList2 = new ArrayList<>();
            X509Certificate x509Certificate = x509CertificateArr[0];
            TrustAnchor findTrustAnchorBySubjectAndPublicKey = findTrustAnchorBySubjectAndPublicKey(x509Certificate);
            if (findTrustAnchorBySubjectAndPublicKey != null) {
                arrayList2.add(findTrustAnchorBySubjectAndPublicKey);
                hashSet.add(findTrustAnchorBySubjectAndPublicKey.getTrustedCert());
            } else {
                arrayList.add(x509Certificate);
            }
            hashSet.add(x509Certificate);
            return checkTrustedRecursive(x509CertificateArr, bArr, bArr2, str2, z, arrayList, arrayList2, hashSet);
        }
        throw new CertificateException(this.err);
    }

    private List<X509Certificate> checkTrustedRecursive(X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2, String str, boolean z, ArrayList<X509Certificate> arrayList, ArrayList<TrustAnchor> arrayList2, Set<X509Certificate> set) {
        X509Certificate trustedCert = arrayList2.isEmpty() ? arrayList.get(arrayList.size() - 1) : arrayList2.get(arrayList2.size() - 1).getTrustedCert();
        checkBlocklist(trustedCert);
        if (trustedCert.getIssuerDN().equals(trustedCert.getSubjectDN())) {
            return verifyChain(arrayList, arrayList2, str, z, bArr, bArr2);
        }
        boolean z2 = false;
        CertificateException e = null;
        for (TrustAnchor trustAnchor : sortPotentialAnchors(findAllTrustAnchorsByIssuerAndSignature(trustedCert))) {
            X509Certificate trustedCert2 = trustAnchor.getTrustedCert();
            if (!set.contains(trustedCert2)) {
                set.add(trustedCert2);
                arrayList2.add(trustAnchor);
                try {
                    return checkTrustedRecursive(x509CertificateArr, bArr, bArr2, str, z, arrayList, arrayList2, set);
                } catch (CertificateException e2) {
                    e = e2;
                    arrayList2.remove(arrayList2.size() - 1);
                    set.remove(trustedCert2);
                    z2 = true;
                }
            }
        }
        if (!arrayList2.isEmpty()) {
            if (z2) {
                throw e;
            }
            return verifyChain(arrayList, arrayList2, str, z, bArr, bArr2);
        }
        for (int i = 1; i < x509CertificateArr.length; i++) {
            X509Certificate x509Certificate = x509CertificateArr[i];
            if (!set.contains(x509Certificate) && trustedCert.getIssuerDN().equals(x509Certificate.getSubjectDN())) {
                try {
                    x509Certificate.checkValidity();
                    ChainStrengthAnalyzer.checkCert(x509Certificate);
                    set.add(x509Certificate);
                    arrayList.add(x509Certificate);
                    try {
                        return checkTrustedRecursive(x509CertificateArr, bArr, bArr2, str, z, arrayList, arrayList2, set);
                    } catch (CertificateException e3) {
                        e = e3;
                        set.remove(x509Certificate);
                        arrayList.remove(arrayList.size() - 1);
                    }
                } catch (CertificateException e4) {
                    StringBuilder d = b.d("Unacceptable certificate: ");
                    d.append(x509Certificate.getSubjectX500Principal());
                    e = new CertificateException(d.toString(), e4);
                }
            }
        }
        for (TrustAnchor trustAnchor2 : sortPotentialAnchors(this.intermediateIndex.findAllByIssuerAndSignature(trustedCert))) {
            X509Certificate trustedCert3 = trustAnchor2.getTrustedCert();
            if (!set.contains(trustedCert3)) {
                set.add(trustedCert3);
                arrayList.add(trustedCert3);
                try {
                    return checkTrustedRecursive(x509CertificateArr, bArr, bArr2, str, z, arrayList, arrayList2, set);
                } catch (CertificateException e5) {
                    e = e5;
                    arrayList.remove(arrayList.size() - 1);
                    set.remove(trustedCert3);
                }
            }
        }
        if (e != null) {
            throw e;
        }
        throw new CertificateException(new CertPathValidatorException("Trust anchor for certification path not found.", null, this.factory.generateCertPath(arrayList), -1));
    }

    private Set<TrustAnchor> findAllTrustAnchorsByIssuerAndSignature(X509Certificate x509Certificate) {
        ConscryptCertStore conscryptCertStore;
        ?? findAllByIssuerAndSignature = this.trustedCertificateIndex.findAllByIssuerAndSignature(x509Certificate);
        if (findAllByIssuerAndSignature.isEmpty() && (conscryptCertStore = this.trustedCertificateStore) != null) {
            Set<X509Certificate> findAllIssuers = conscryptCertStore.findAllIssuers(x509Certificate);
            if (findAllIssuers.isEmpty()) {
                return findAllByIssuerAndSignature;
            }
            findAllByIssuerAndSignature = new HashSet(findAllIssuers.size());
            for (X509Certificate x509Certificate2 : findAllIssuers) {
                findAllByIssuerAndSignature.add(this.trustedCertificateIndex.index(x509Certificate2));
            }
        }
        return findAllByIssuerAndSignature;
    }

    private TrustAnchor findTrustAnchorBySubjectAndPublicKey(X509Certificate x509Certificate) {
        X509Certificate trustAnchor;
        TrustAnchor findBySubjectAndPublicKey = this.trustedCertificateIndex.findBySubjectAndPublicKey(x509Certificate);
        if (findBySubjectAndPublicKey != null) {
            return findBySubjectAndPublicKey;
        }
        ConscryptCertStore conscryptCertStore = this.trustedCertificateStore;
        if (conscryptCertStore == null || (trustAnchor = conscryptCertStore.getTrustAnchor(x509Certificate)) == null) {
            return null;
        }
        return new TrustAnchor(trustAnchor, null);
    }

    public static synchronized ConscryptHostnameVerifier getDefaultHostnameVerifier() {
        ConscryptHostnameVerifier conscryptHostnameVerifier;
        synchronized (TrustManagerImpl.class) {
            conscryptHostnameVerifier = defaultHostnameVerifier;
        }
        return conscryptHostnameVerifier;
    }

    private static SSLSession getHandshakeSessionOrThrow(SSLSocket sSLSocket) {
        SSLSession handshakeSession = sSLSocket.getHandshakeSession();
        if (handshakeSession != null) {
            return handshakeSession;
        }
        throw new CertificateException("Not in handshake; no session available");
    }

    private ConscryptHostnameVerifier getHttpsVerifier() {
        ConscryptHostnameVerifier conscryptHostnameVerifier = this.hostnameVerifier;
        return conscryptHostnameVerifier != null ? conscryptHostnameVerifier : Platform.getDefaultHostnameVerifier();
    }

    private static byte[] getOcspDataFromSession(SSLSession sSLSession) {
        List<byte[]> list;
        if (sSLSession instanceof ConscryptSession) {
            list = ((ConscryptSession) sSLSession).getStatusResponses();
        } else {
            try {
                Method declaredMethod = sSLSession.getClass().getDeclaredMethod("getStatusResponses", new Class[0]);
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(sSLSession, new Object[0]);
                if (invoke instanceof List) {
                    list = (List) invoke;
                }
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException unused) {
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
            list = null;
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private byte[] getTlsSctDataFromSession(SSLSession sSLSession) {
        if (sSLSession instanceof ConscryptSession) {
            return ((ConscryptSession) sSLSession).getPeerSignedCertificateTimestamp();
        }
        try {
            Method declaredMethod = sSLSession.getClass().getDeclaredMethod("getPeerSignedCertificateTimestamp", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(sSLSession, new Object[0]);
            if (invoke instanceof byte[]) {
                return (byte[]) invoke;
            }
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException unused) {
            return null;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static synchronized void setDefaultHostnameVerifier(ConscryptHostnameVerifier conscryptHostnameVerifier) {
        synchronized (TrustManagerImpl.class) {
            defaultHostnameVerifier = conscryptHostnameVerifier;
        }
    }

    private void setOcspResponses(PKIXParameters pKIXParameters, X509Certificate x509Certificate, byte[] bArr) {
        if (bArr == null) {
            return;
        }
        PKIXRevocationChecker pKIXRevocationChecker = null;
        ArrayList arrayList = new ArrayList(pKIXParameters.getCertPathCheckers());
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PKIXCertPathChecker pKIXCertPathChecker = (PKIXCertPathChecker) it.next();
            if (pKIXCertPathChecker instanceof PKIXRevocationChecker) {
                pKIXRevocationChecker = (PKIXRevocationChecker) pKIXCertPathChecker;
                break;
            }
        }
        if (pKIXRevocationChecker == null) {
            try {
                pKIXRevocationChecker = (PKIXRevocationChecker) this.validator.getRevocationChecker();
                arrayList.add(pKIXRevocationChecker);
                pKIXRevocationChecker.setOptions(Collections.singleton(PKIXRevocationChecker.Option.ONLY_END_ENTITY));
            } catch (UnsupportedOperationException unused) {
                return;
            }
        }
        pKIXRevocationChecker.setOcspResponses(Collections.singletonMap(x509Certificate, bArr));
        pKIXParameters.setCertPathCheckers(arrayList);
    }

    private static Collection<TrustAnchor> sortPotentialAnchors(Set<TrustAnchor> set) {
        if (set.size() <= 1) {
            return set;
        }
        ArrayList arrayList = new ArrayList(set);
        Collections.sort(arrayList, TRUST_ANCHOR_COMPARATOR);
        return arrayList;
    }

    private static Set<TrustAnchor> trustAnchors(X509Certificate[] x509CertificateArr) {
        HashSet hashSet = new HashSet(x509CertificateArr.length);
        for (X509Certificate x509Certificate : x509CertificateArr) {
            hashSet.add(new TrustAnchor(x509Certificate, null));
        }
        return hashSet;
    }

    private List<X509Certificate> verifyChain(List<X509Certificate> list, List<TrustAnchor> list2, String str, boolean z, byte[] bArr, byte[] bArr2) {
        try {
            CertPath generateCertPath = this.factory.generateCertPath(list);
            if (list2.isEmpty()) {
                throw new CertificateException(new CertPathValidatorException("Trust anchor for certification path not found.", null, generateCertPath, -1));
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(list);
            for (TrustAnchor trustAnchor : list2) {
                arrayList.add(trustAnchor.getTrustedCert());
            }
            CertPinManager certPinManager = this.pinManager;
            if (certPinManager != null) {
                certPinManager.checkChainPinning(str, arrayList);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                checkBlocklist((X509Certificate) it.next());
            }
            if (!z && (this.ctEnabledOverride || (str != null && Platform.isCTVerificationRequired(str)))) {
                checkCT(str, arrayList, bArr, bArr2);
            }
            if (list.isEmpty()) {
                return arrayList;
            }
            ChainStrengthAnalyzer.check(list);
            try {
                HashSet hashSet = new HashSet();
                hashSet.add(list2.get(0));
                PKIXParameters pKIXParameters = new PKIXParameters(hashSet);
                pKIXParameters.setRevocationEnabled(false);
                X509Certificate x509Certificate = list.get(0);
                setOcspResponses(pKIXParameters, x509Certificate, bArr);
                pKIXParameters.addCertPathChecker(new ExtendedKeyUsagePKIXCertPathChecker(z, x509Certificate, null));
                this.validator.validate(generateCertPath, pKIXParameters);
                for (int i = 1; i < list.size(); i++) {
                    this.intermediateIndex.index(list.get(i));
                }
                return arrayList;
            } catch (InvalidAlgorithmParameterException e) {
                throw new CertificateException("Chain validation failed", e);
            } catch (CertPathValidatorException e2) {
                throw new CertificateException("Chain validation failed", e2);
            }
        } catch (CertificateException e3) {
            Logger logger2 = logger;
            StringBuilder d = b.d("Rejected candidate cert chain due to error: ");
            d.append(e3.getMessage());
            logger2.fine(d.toString());
            throw e3;
        }
    }

    public List<X509Certificate> checkClientTrusted(X509Certificate[] x509CertificateArr, String str, String str2) {
        return checkTrusted(x509CertificateArr, null, null, str, str2, true);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        checkTrusted(x509CertificateArr, str, null, null, true);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) {
        SSLSession sSLSession;
        SSLParameters sSLParameters;
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLSession handshakeSessionOrThrow = getHandshakeSessionOrThrow(sSLSocket);
            sSLParameters = sSLSocket.getSSLParameters();
            sSLSession = handshakeSessionOrThrow;
        } else {
            sSLSession = null;
            sSLParameters = null;
        }
        checkTrusted(x509CertificateArr, str, sSLSession, sSLParameters, true);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) {
        SSLSession handshakeSession = sSLEngine.getHandshakeSession();
        if (handshakeSession == null) {
            throw new CertificateException("Not in handshake; no session available");
        }
        checkTrusted(x509CertificateArr, str, handshakeSession, sSLEngine.getSSLParameters(), true);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, String str, String str2) {
        return checkTrusted(x509CertificateArr, null, null, str, str2, false);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLSession sSLSession) {
        return checkTrusted(x509CertificateArr, str, sSLSession, null, false);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        checkTrusted(x509CertificateArr, str, null, null, false);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) {
        getTrustedChainForServer(x509CertificateArr, str, socket);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) {
        getTrustedChainForServer(x509CertificateArr, str, sSLEngine);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        X509Certificate[] x509CertificateArr = this.acceptedIssuers;
        return x509CertificateArr != null ? (X509Certificate[]) x509CertificateArr.clone() : acceptedIssuers(this.rootKeyStore);
    }

    public ConscryptHostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public List<X509Certificate> getTrustedChainForServer(X509Certificate[] x509CertificateArr, String str, Socket socket) {
        SSLSession sSLSession;
        SSLParameters sSLParameters;
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLSession handshakeSessionOrThrow = getHandshakeSessionOrThrow(sSLSocket);
            sSLParameters = sSLSocket.getSSLParameters();
            sSLSession = handshakeSessionOrThrow;
        } else {
            sSLSession = null;
            sSLParameters = null;
        }
        return checkTrusted(x509CertificateArr, str, sSLSession, sSLParameters, false);
    }

    public List<X509Certificate> getTrustedChainForServer(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) {
        SSLSession handshakeSession = sSLEngine.getHandshakeSession();
        if (handshakeSession != null) {
            return checkTrusted(x509CertificateArr, str, handshakeSession, sSLEngine.getSSLParameters(), false);
        }
        throw new CertificateException("Not in handshake; no session available");
    }

    public void handleTrustStorageUpdate() {
        X509Certificate[] x509CertificateArr = this.acceptedIssuers;
        if (x509CertificateArr == null) {
            this.trustedCertificateIndex.reset();
        } else {
            this.trustedCertificateIndex.reset(trustAnchors(x509CertificateArr));
        }
    }

    public void setCTEnabledOverride(boolean z) {
        this.ctEnabledOverride = z;
    }

    public void setCTPolicy(CTPolicy cTPolicy) {
        this.ctPolicy = cTPolicy;
    }

    public void setCTVerifier(CTVerifier cTVerifier) {
        this.ctVerifier = cTVerifier;
    }

    public void setHostnameVerifier(ConscryptHostnameVerifier conscryptHostnameVerifier) {
        this.hostnameVerifier = conscryptHostnameVerifier;
    }
}
