package com.streaming;

import com.streaming.domain.Stream;
import org.joda.time.DateTime;

public class CacheControl {

    private static int MAX_AGE = 900;
    private static int MAX_SHARED_AGE = 300;

    public static String calculateCacheHeader(Stream stream) {
        final long ttl = (stream.getStartTime().getMillis() - DateTime.now().getMillis()) / 1000;
        if (stream.getStartTime().isBeforeNow()) {
            return "public, max-age=" + MAX_AGE + ", s-maxage=" + MAX_SHARED_AGE;
        } else {
            return "public, max-age=" + getMaxAge(ttl) + ", s-maxage=" + getSMaxAge(ttl);
        }
    }

    private static int getMaxAge(long ttl) {
        if (ttl * 0.8 < MAX_AGE)
            return (int) Math.round(ttl * 0.8);
        else
            return MAX_AGE;
    }

    private static int getSMaxAge(long ttl) {
        if (ttl * 0.8 < MAX_SHARED_AGE)
            return (int) Math.round(ttl * 0.8);
        else
            return MAX_SHARED_AGE;
    }


}


//Expires: Mon, 29 Apr 2013 21:44:55 GMT
//        Cache-Control: max-age=0, no-cache, no-store
//        Pragma: no-cache