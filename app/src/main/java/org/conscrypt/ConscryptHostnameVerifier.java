package org.conscrypt;

import java.security.cert.X509Certificate;
import javax.net.ssl.SSLSession;
public interface ConscryptHostnameVerifier {
    boolean verify(X509Certificate[] x509CertificateArr, String str, SSLSession sSLSession);
}
