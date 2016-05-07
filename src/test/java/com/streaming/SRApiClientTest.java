package com.streaming;

import com.streaming.domain.Stream;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SRApiClientTest {

    @Mock
    Args args;
    @Mock
    HttpClient client;
    @Mock
    HttpResponse response;
    @Mock
    StatusLine statusLine;

    @Ignore
    @Test
    public void itShouldUseTheArgsApiUrl() throws Exception {
        when(args.getPlatformApi()).thenReturn("https://api.streamingrocket.com");
        when(client.execute(any())).thenReturn(response);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        final Stream stream = SRApiClient.getStream(4, args, client);
        verify(args).getPlatformApi();
        System.out.println(stream);
    }
}