package com.streaming.http;

import com.streaming.PlayerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public static String getResponseContentAsString(final HttpResponse response) {
        StringBuilder sb = new StringBuilder();
        try {
            final InputStream is = response.getEntity().getContent();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n");
            }

            is.close();
        } catch (IOException | UnsupportedOperationException e) {
            LOG.error("Exception thrown whilst getting HLS manifest", e);
        }
        return sb.toString();
    }

}
