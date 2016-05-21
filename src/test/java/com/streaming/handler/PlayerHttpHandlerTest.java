package com.streaming.handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicStatusLine;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayerHttpHandlerTest {


    private PlayerHttpHandler underTest;

    @Mock
    private HttpClient srManifestClient;
    @Mock
    private HttpResponse srManifestResponse;
    @Mock
    private HttpEntity srManifestResponseEntity;


    @Mock
    private HttpClient srApiClient;
    @Mock
    private HttpResponse srApiResponse;
    @Mock
    private HttpEntity srApiResponseEntity;

    @Mock
    private Request request;
    @Mock
    private Response response;
    @Mock
    private Writer responseWriter;


    @Before
    public void setup() throws IOException {
        underTest = new PlayerHttpHandler(srApiClient, srManifestClient);
        when(response.getWriter()).thenReturn(responseWriter);
        
        when(srApiClient.execute(any(HttpGet.class))).thenReturn(srApiResponse);
        when(srApiResponse.getStatusLine()).thenReturn(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        when(srApiResponse.getEntity()).thenReturn(srApiResponseEntity);
        when(srApiResponseEntity.getContent()).thenReturn(new FileInputStream("src/test/resources/api_4.json"));

        when(srManifestClient.execute(any(HttpGet.class))).thenReturn(srManifestResponse);
        when(srManifestResponse.getStatusLine()).thenReturn(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        when(srManifestResponse.getEntity()).thenReturn(srManifestResponseEntity);
        when(srManifestResponseEntity.getContent()).thenReturn(new FileInputStream("src/test/resources/testingHLS-master.m3u8"));
    }

    @Test
    public void blach() throws Exception {
        when(request.getParameter("id")).thenReturn("4");
        underTest.service(request, response);
    }
}