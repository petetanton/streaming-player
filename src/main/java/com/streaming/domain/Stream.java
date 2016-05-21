package com.streaming.domain;

import org.joda.time.DateTime;

public class Stream {

    private int status;
    private int streamId;
    private String streamTitle;
    private String streamType;
    private String streamDescription;
    private StreamUrls streamUrls;
    private boolean dvrEnabled;
    private String organisation;
    private int security;
    private DateTime startTime;
    private String thumbnailUrl;
    private String embedUrl;
    private int interactionCount;
    private int originId;

    private Stream() {
    }

    public boolean isDvrEnabled() {
        return dvrEnabled;
    }

    public void setDvrEnabled(boolean dvrEnabled) {
        this.dvrEnabled = dvrEnabled;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public void setStreamTitle(String streamTitle) {
        this.streamTitle = streamTitle;
    }

    public String getStreamType() {
        return streamType;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public int getSecurity() {
        return security;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusAsString() {
        switch (this.status) {
            case 1:
                return "awaiting scheduler";
            case 2:
                return "scheduled";
            case 3:
                return "pending";
            case 4:
                return "live";
            case 5:
                return "finished";
            case 6:
                return "finished with VOD";
            case 7:
                return "vod";
            default:
                return "status not defined";
        }
    }

    public void setStatusAsString(String statusAsString) {
//        This method is empty to trick jackson
    }

    public int getStreamId() {
        return streamId;
    }

    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }

    public String getStreamDescription() {
        return streamDescription;
    }

    public void setStreamDescription(String streamDescription) {
        this.streamDescription = streamDescription;
    }

    public StreamUrls getStreamUrls() {
        return streamUrls;
    }

    public void setStreamUrls(StreamUrls streamUrls) {
        this.streamUrls = streamUrls;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public int getInteractionCount() {
        return interactionCount;
    }

    public void setInteractionCount(int interactionCount) {
        this.interactionCount = interactionCount;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

}
