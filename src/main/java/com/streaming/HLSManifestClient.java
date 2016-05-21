package com.streaming;

import com.streaming.domain.hls.HLSManifest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class HLSManifestClient {

    private static final Logger LOG = Logger.getLogger(HLSManifestClient.class);

    private HLSManifestClient() {
    }

    public static HLSManifest getHLSManifest(String manifestUrl, HttpClient client) throws PlayerException {
        final URI uri;
        try {
            final URIBuilder uriBuilder = new URIBuilder(manifestUrl);
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            LOG.error("An error occurred whilst building the manifest URL", e);
            throw new PlayerException(e);
        }
        LOG.info("requesting: " + uri.toString());
        HttpGet request = new HttpGet(uri);
        request.setHeader("Accept", "*/*");
        request.setHeader("User-Agent", "streaming-player");

        final HttpResponse response;
        try {
            response = client.execute(request);
        } catch (Exception e) {
            LOG.error("An error occurred whist truing to GET from platform api", e);
            throw new PlayerException("An error occurred whist truing to GET from platform api", e);
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            LOG.error("Got a non 200 response from platform api, response was [" + response.getStatusLine().getStatusCode() + "] " + response.getStatusLine().getReasonPhrase());
            throw new PlayerException(response.getStatusLine().getReasonPhrase());
        }

        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n");
            }
        } catch (IOException | UnsupportedOperationException e) {
            LOG.error("Exception thrown whilst getting HLS manifest", e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (Exception e) {
                    LOG.error(e);
                }
        }

        final HLSManifest manifest;
        try {
            manifest = HLSManifest.generateHLSManifest(sb.toString());
        } catch (AssertionError e) {
            LOG.error("There was a problem parsing an HLS Manifest", e);
            throw new PlayerException("There was a problem parsing the HLS Manifest", e);
        }
        return manifest;
    }
}
