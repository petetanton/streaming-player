package com.streaming.domain.stats;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class ContainerBitrateEvent {

    private int height;
    private int width;
    private int bitrate;
    private int level;

    public int getBitrate() {
        return bitrate;
    }

    public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }

    public int getWidth() {
        return width;
    }
}
