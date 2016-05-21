package com.streaming;

import com.streaming.domain.Stream;

public class CacheControl {
    private static final int MAX_AGE = 20;
    private static final int MAX_SHARED_AGE = 10;

    public static String getDefaultCacheHeader() {
        return "public, max-age=" + MAX_AGE + ", s-maxage=" + MAX_SHARED_AGE;
    }

    public static String getCacheHeaderForPlayer(Stream stream) {
        if (stream.getStatusAsInt() >= 3) {
            return getDefaultCacheHeader();
        } else {
            return "public, max-age=120, s-maxage=60";
        }
    }

}
