package com.streaming.domain;

import java.util.HashMap;

public class StreamTypes {
    private final HashMap<String, StreamBitrates> streamBitrates = new HashMap<>();
    public void addStreamBitrate(String name, StreamBitrates streamBitrate) {
        streamBitrates.put(name, streamBitrate);
    }

    public HashMap<String, StreamBitrates> getStreamBitrates() {
        return streamBitrates;
    }
}
