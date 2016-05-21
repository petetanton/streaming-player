package com.streaming.http;

import com.streaming.PlayerException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.log4j.Logger;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public final class HttpUtils {
    private static final Logger LOG = Logger.getLogger(HttpUtils.class);

    private HttpUtils() {
    }

    public static HttpClient buildClient() throws PlayerException {
        try {
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContextBuilder.build());

            final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            httpClientBuilder.setSSLSocketFactory(sslsf);
            return httpClientBuilder.build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            LOG.error("An exception occurred whilst building an http client", e);
            throw new PlayerException("An exception occurred whilst building an http client", e);
        }
    }

}
