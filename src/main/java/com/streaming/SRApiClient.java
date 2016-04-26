package com.streaming;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.streaming.domain.Stream;
import com.streaming.http.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.io.InputStream;
import java.net.URI;

public class SRApiClient {

    public static Stream getStream(int streamId) throws Exception {
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
        HttpClient client = HttpUtils.buildClient();
        final HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() != 200) {
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
            throw new Exception(e);
        }
        return stream;
    }
}
