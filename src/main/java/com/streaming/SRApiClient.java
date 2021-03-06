package com.streaming;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.streaming.domain.Stream;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.URI;

public class SRApiClient {

    private static final Logger LOG = Logger.getLogger(SRApiClient.class);

    public static Stream getStream(int streamId, HttpClient client) throws PlayerException {
        final URIBuilder uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost("api.streamingrocket.com")
                .setPath("/streams/stream")
                .setParameter("id", String.valueOf(streamId));

        final HttpResponse response;
        final InputStream is;

        try {
            final URI uri = uriBuilder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Accept", "application/json");
            request.setHeader("User-Agent", "streaming-player");

            response = client.execute(request);
            is = response.getEntity().getContent();
        } catch (Exception e) {
            LOG.error("An error occurred whist trying to GET from platform api", e);
            throw new PlayerException("An error occurred whist trying to GET from platform api", e);
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            LOG.error("Got a non 200 response from platform api, response was [" + response.getStatusLine().getStatusCode() + "] " + response.getStatusLine().getReasonPhrase());
            throw new PlayerException(response.getStatusLine().getReasonPhrase());
        }

        final Stream stream;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.registerModule(new JodaModule());
            stream = mapper.readValue(is, Stream.class);
        } catch (Exception e) {
            LOG.error(e);
            throw new PlayerException("An issue occurred whilst parsing the API response", e);
        }
        return stream;
    }
}
