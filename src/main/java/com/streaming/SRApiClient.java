package com.streaming;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.streaming.domain.Stream;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.URI;

public class SRApiClient {

    private static final Logger LOG = Logger.getLogger(SRApiClient.class);

    SRApiClient() {
    }

    public static Stream getStream(int streamId, CloseableHttpClient client) throws Exception {
        final URIBuilder uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost("api.streamingrocket.com")
                .setPath("/streams/stream")
                .setParameter("id", String.valueOf(streamId));
        final URI uri = uriBuilder.build();
        System.out.println(uri.toString());
        HttpGet request = new HttpGet(uri);
        request.setHeader("Accept", "application/json");
        request.setHeader("User-Agent", "streaming-player");

        final HttpResponse response;
        try {
            response = client.execute(request);
        } catch (Exception e) {
            LOG.error("An error occurred whist truing to GET from platform api", e);
            throw new Exception("An error occurred whist truing to GET from platform api", e);
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            LOG.error("Got a non 200 response from platform api, response was [" + response.getStatusLine().getStatusCode() + "] " + response.getStatusLine().getReasonPhrase());
            throw new Exception(response.getStatusLine().getReasonPhrase());
        }
        final InputStream is = response.getEntity().getContent();
        final Stream stream;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.registerModule(new JodaModule());
            stream = mapper.readValue(is, Stream.class);
        } catch (Exception e) {
            LOG.error(e);
            throw new Exception(e);
        }
        return stream;
    }
}
