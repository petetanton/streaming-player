package com.streaming;

import com.streaming.domain.HLSManifest;
import org.apache.http.client.HttpClient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HLSManifestClientTest {

    @Mock
    HttpClient client;

    @Ignore
    @Test
    public void itGetsADVRStream() throws Exception {
        final String url = "https://d3jedn0s4r9s07.cloudfront.net/hls/1-10/testingHLS-master.m3u8";


        final HLSManifest hlsManifest = HLSManifestClient.getHLSManifest(url, client);

        System.out.println(hlsManifest.generateClapprSelectorConfig());

    }

}