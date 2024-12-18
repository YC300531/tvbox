package org.conscrypt;

import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLSession;
public interface ConscryptSession extends SSLSession {
    String getApplicationProtocol();

    @Override
    X509Certificate[] getPeerCertificates();

    byte[] getPeerSignedCertificateTimestamp();

    String getRequestedServerName();

    List<byte[]> getStatusResponses();
}
