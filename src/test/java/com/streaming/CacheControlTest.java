package com.streaming;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CacheControlTest {

    @Test
    public void testCacheTimeIsSet() {
        final String actual = CacheControl.getDefaultCacheHeader();
        final String expected = "public, max-age=20, s-maxage=10";
        assertEquals(expected, actual);
    }

}