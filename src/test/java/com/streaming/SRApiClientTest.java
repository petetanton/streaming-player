package com.streaming;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SRApiClientTest {

    @Mock
    private HttpClient client;

    @Mock
    private HttpResponse response;

    @Mock
    private StatusLine statusLine;

    @Mock
    private HttpEntity httpEntity;

    @Test
    public void itHandlesAnHttpClientError() throws PlayerException, IOException {
        when(client.execute(any(HttpGet.class))).thenThrow(new IOException("some message"));

        try {
            SRApiClient.getStream(4, client);
            fail("Expected an error");
        } catch (PlayerException e) {
            assertEquals("An error occurred whist trying to GET from platform api", e.getMessage());
            assertTrue(e.getCause() instanceof IOException);
        }
    }

    @Test
    public void itHandlesA500Response() throws IOException, PlayerException {
        when(client.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(500);
        when(statusLine.getReasonPhrase()).thenReturn("reason");
        when(response.getEntity()).thenReturn(httpEntity);

        try {
            SRApiClient.getStream(4, client);
            fail("Expected an exception");
        } catch (PlayerException e) {
            assertEquals("reason", e.getMessage());
        }
    }
}