package com.streaming.domain.stats;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "sr-player-stats")
public class PlayerStat {

    @DynamoDBHashKey(attributeName = "update_timestamp")
    private long updateTimestamp;

    @DynamoDBRangeKey(attributeName = "player_uuid")
    private String playerUUID;

    @DynamoDBIndexHashKey(attributeName = "stream_id")
    private String streamId;

    @DynamoDBAttribute(attributeName = "rendition")
    private String rendition;

    @DynamoDBAttribute(attributeName = "bitrate_event")
    private ContainerBitrateEvent containerBitrateEvent;


    public ContainerBitrateEvent getContainerBitrateEvent() {
        return containerBitrateEvent;
    }

    public void setContainerBitrateEvent(ContainerBitrateEvent containerBitrateEvent) {
        this.containerBitrateEvent = containerBitrateEvent;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getRendition() {
        return rendition;
    }

    public void setRendition(String rendition) {
        this.rendition = rendition;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public long getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(long updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
