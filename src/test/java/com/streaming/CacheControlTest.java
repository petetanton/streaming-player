package com.streaming;

import com.streaming.domain.Stream;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheControlTest {

    @Mock private Stream stream;

    @Test
    public void testCacheTimeIsSetWhen1Min() {
        when(stream.getStartTime()).thenReturn(DateTime.now().plusMinutes(1).plusSeconds(1));
        final String actual = CacheControl.calculateCacheHeader(stream);
        final String expected = "public, max-age=48, s-maxage=48";
        assertEquals(expected, actual);
    }

    @Test
    public void itShouldSetCacheTimeWhenOver375Seconds() {
        when(stream.getStartTime()).thenReturn(DateTime.now().plusSeconds(381).plusMillis(400));
        final String actual = CacheControl.calculateCacheHeader(stream);
        final String expected = "public, max-age=305, s-maxage=300";
        assertEquals(expected, actual);
    }

    @Test
    public void itShouldSetCacheTimeWhenOver15Mins() {
        when(stream.getStartTime()).thenReturn(DateTime.now().plusMinutes(16).plusMillis(400));
        final String actual = CacheControl.calculateCacheHeader(stream);
        final String expected = "public, max-age=768, s-maxage=300";
        assertEquals(expected, actual);
    }

    @Test
    public void itShouldSetCacheTimeWhenOver19Mins() {
        when(stream.getStartTime()).thenReturn(DateTime.now().plusMinutes(20));
        final String actual = CacheControl.calculateCacheHeader(stream);
        final String expected = "public, max-age=900, s-maxage=300";
        assertEquals(expected, actual);
    }

}