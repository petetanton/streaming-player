package com.streaming.domain.hls;

import java.util.HashMap;

public class StreamUrls {

    private final HashMap<String, StreamTypes> streamTypes = new HashMap<>();

    public HashMap<String, StreamTypes> getStreamTypes() {
        return streamTypes;
    }

    public void addStreamType(String name, StreamTypes streamType) {
        streamTypes.put(name, streamType);

    }
}