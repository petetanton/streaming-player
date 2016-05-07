package com.streaming;

import com.streaming.domain.Stream;
import org.joda.time.DateTime;

public class CacheControl {

    private static final int MAX_AGE = 900;
    private static final int MAX_SHARED_AGE = 300;

    public static String calculateCacheHeader(Stream stream) {
        final long ttl = (stream.getStartTime().getMillis() - DateTime.now().getMillis()) / 1000;
        if (stream.getStartTime().isBeforeNow()) {
            return "public, max-age=" + MAX_AGE + ", s-maxage=" + MAX_SHARED_AGE;
        } else {
            return "public, max-age=" + getMaxAge(ttl) + ", s-maxage=" + getSMaxAge(ttl);
        }
    }

    private static int getMaxAge(long ttl) {
        return Math.min((int) Math.round(ttl * 0.8), MAX_AGE);
    }

    private static int getSMaxAge(long ttl) {
        return Math.min((int) Math.round(ttl * 0.8), MAX_SHARED_AGE);
    }
}
