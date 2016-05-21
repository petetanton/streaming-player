package com.streaming;

import com.streaming.domain.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheControlTest {

    @Mock
    private Stream stream;

    @Test
    public void testCacheTimeIsSet() {
        final String actual = CacheControl.getDefaultCacheHeader();
        final String expected = "public, max-age=20, s-maxage=10";
        assertEquals(expected, actual);
    }

    @Test
    public void testCacheIsSetForPreLiveStreams() {
        for (int i = 1; i < 8; i++) {
            when(stream.getStatus()).thenReturn(i);
            verifyCacheForStatus(i, CacheControl.getCacheHeaderForPlayer(stream));
        }
    }


    private void verifyCacheForStatus(int i, String cacheHeader) {
        if (i < 3) {
            assertEquals("failed for stream status: " + i, "public, max-age=120, s-maxage=60", cacheHeader);
        } else if (i >= 3) {
            assertEquals("failed for stream status: " + i, "public, max-age=20, s-maxage=10", cacheHeader);
        } else {
            fail("stream id " + i + " was not expected");
        }
    }

}