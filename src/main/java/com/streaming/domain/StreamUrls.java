package com.streaming.domain;

import java.util.HashMap;
import java.util.Map;

public class StreamUrls {

    private final Map<String, StreamTypes> streamTypes = new HashMap<>();

    public Map<String, StreamTypes> getStreamTypes() {
        return streamTypes;
    }

    public void addStreamType(String name, StreamTypes streamType) {
        streamTypes.put(name, streamType);

    }
}