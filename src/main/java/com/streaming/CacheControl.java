package com.streaming;

public class CacheControl {
    private static final int MAX_AGE = 20;
    private static final int MAX_SHARED_AGE = 10;
    CacheControl() {
    }

    public static String getDefaultCacheHeader() {
        return "public, max-age=" + MAX_AGE + ", s-maxage=" + MAX_SHARED_AGE;
    }

}
