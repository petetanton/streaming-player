package com.streaming.domain;

import java.util.HashMap;
import java.util.Map;

public class StreamUrls {

    private final Map<String, StreamTypes> hls = new HashMap<>();
    private final Map<String, StreamTypes> dash = new HashMap<>();

    public Map<String, StreamTypes> getDash() {
        return dash;
    }

    public Map<String, StreamTypes> getHls() {
        return hls;
    }

}