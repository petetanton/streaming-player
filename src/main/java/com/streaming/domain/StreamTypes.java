package com.streaming.domain;

import java.util.HashMap;
import java.util.Map;

public class StreamTypes {
    private final Map<String, StreamBitrates> streamBitrates = new HashMap<>();

    public void addStreamBitrate(String name, StreamBitrates streamBitrate) {
        streamBitrates.put(name, streamBitrate);
    }

    public Map<String, StreamBitrates> getStreamBitrates() {
        return streamBitrates;
    }
}
