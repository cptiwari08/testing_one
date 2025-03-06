package com.atotech.apitests.api.common.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

public class SSLIssuesIgnoringHttpClientFactory {

    public static HttpClient createHttpClient() {
            SSLContext sslContext = createSSLContext();

            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            httpClientBuilder.setSSLContext(sslContext);

            HostnameVerifier dummyHostnameVerifier = new DummyHostnameVerifier();
            httpClientBuilder.setSSLHostnameVerifier(dummyHostnameVerifier);
            httpClientBuilder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext, dummyHostnameVerifier));

            return httpClientBuilder.build();
    }

    private static SSLContext createSSLContext() {
        try {
            return new SSLContextBuilder().loadTrustMaterial(null,
                    (x509Certificates, s) -> true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
