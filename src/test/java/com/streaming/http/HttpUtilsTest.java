package com.streaming.http;

import org.apache.http.client.HttpClient;
import org.junit.Test;

import static org.junit.Assert.*;

public class HttpUtilsTest {

    @Test
    public void itBuildsAClient() throws Exception {
        final HttpClient httpClient = HttpUtils.buildClient();
        assertTrue(httpClient != null);
    }
}