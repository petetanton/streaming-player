package com.streaming;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SRApiClientTest {

    @Mock
    HttpClient client;
    @Mock
    HttpResponse response;
    @Mock
    StatusLine statusLine;

    @Ignore
    @Test
    public void itShouldUseTheArgsApiUrl() throws Exception {
        when(client.execute(any())).thenReturn(response);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
    }
}