package com.streaming;

import com.streaming.domain.hls.HLSManifest;
import com.streaming.domain.hls.HLSStreamInf;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HLSManifestClientTest {

    private static final String NICE_URL = "http://somewhere.com/manifest";

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse response;
    @Mock
    private StatusLine statusLine;
    @Mock
    private HttpEntity httpEntity;

    @Test
    public void itParsesAResponseWithA200Code() throws PlayerException, IOException {
        when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new FileInputStream("src/test/resources/testingHLS-master.m3u8"));

        final HLSManifest actual = HLSManifestClient.getHLSManifest(NICE_URL, httpClient);


        assertEquals(5, actual.getTargetDuraton());
        assertEquals(0, actual.getMediaSequence());

        for (HLSStreamInf hlsStreamInf : actual.getHlsStreamInf()) {
            assertEquals(1, hlsStreamInf.getProgramId());
            assertTrue(hlsStreamInf.getBandwidth() > 100000);
            assertTrue(hlsStreamInf.getHeight() / 9 == hlsStreamInf.getWidth() / 16);
        }
    }

    @Test
    public void itThrowsAnErrorIfABadUrlIsPassedIn() {
        try {
            HLSManifestClient.getHLSManifest("httt)(86tp://hel/lo.com", httpClient);
            fail("No exception thrown");
        } catch (PlayerException e) {
            assertTrue(e.getCause() instanceof URISyntaxException);
        }
    }

    @Test
    public void itThrowsAPlayerExceptionIfTheHttpClientThrowAnException() {
        try {
            when(httpClient.execute(any(HttpGet.class))).thenThrow(ClientProtocolException.class);
            HLSManifestClient.getHLSManifest(NICE_URL, httpClient);
            fail("Expected an exception");
        } catch (Exception e) {
            assertTrue(e instanceof PlayerException);
        }
    }

}
