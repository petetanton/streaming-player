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
        HttpGet apiGetRequest = new HttpGet(uri);
        apiGetRequest.setHeader("Accept", "application/json");
        HttpClient client = HttpUtils.buildClient();
        final HttpResponse apiResponse = client.execute(apiGetRequest);

        if (apiResponse.getStatusLine().getStatusCode() != 200) {
            throw new Exception(apiResponse.getStatusLine().getReasonPhrase());
        }
        final InputStream is = apiResponse.getEntity().getContent();
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
