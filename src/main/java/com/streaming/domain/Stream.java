package com.streaming.domain;

import org.joda.time.DateTime;

public class Stream {

    private int statusAsInt;
    private String statusAsString;
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

    public Stream(String organisation,
                  int security,
                  DateTime startTime,
                  int statusAsInt,
                  int streamId,
                  String streamTitle,
                  String streamType,
                  String streamDescription,
                  StreamUrls streamUrls,
                  boolean dvrEnabled,
                  String thumbnailUrl,
                  String embedUrl,
                  int interactionCount,
                  int originId,
                  String statusAsString) {
        this.organisation = organisation;
        this.security = security;
        this.startTime = startTime;
        this.statusAsInt = statusAsInt;
        this.streamId = streamId;
        this.streamTitle = streamTitle;
        this.streamType = streamType;
        this.streamDescription = streamDescription;
        this.streamUrls = streamUrls;
        this.dvrEnabled = dvrEnabled;
        this.thumbnailUrl = thumbnailUrl;
        this.embedUrl = embedUrl;
        this.interactionCount = interactionCount;
        this.originId = originId;
        this.statusAsString = statusAsString;
    }

    public Stream() {
    }


    public void setStatusAsString(String statusAsString) {
        this.statusAsString = statusAsString;
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

    public String getStreamType() {
        return streamType;
    }

    public String getOrganisation() {
        return organisation;
    }

    public int getSecurity() {
        return security;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public int getStatusAsInt() {
        return statusAsInt;
    }

    public String getStatusAsString() {
        switch (this.statusAsInt) {
            case(1):
                return "awaiting scheduler";
            case(2):
                return "scheduled";
            case(3):
                return "pending";
            case(4):
                return "live";
            case(5):
                return "finished";
            case(6):
                return "finished with VOD";
            case(7):
                return "vod";
        }
        return "statusAsInt not defined";
    }

    public int getStreamId() {
        return streamId;
    }

    public String getStreamDescription() {
        return streamDescription;
    }

    public StreamUrls getStreamUrls() {
        return streamUrls;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public int getInteractionCount() {
        return interactionCount;
    }

    public int getOriginId() {
        return originId;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public void setInteractionCount(int interactionCount) {
        this.interactionCount = interactionCount;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public void setStatusAsInt(int statusAsInt) {
        this.statusAsInt = statusAsInt;
    }

    public void setStreamDescription(String streamDescription) {
        this.streamDescription = streamDescription;
    }

    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }

    public void setStreamTitle(String streamTitle) {
        this.streamTitle = streamTitle;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public void setStreamUrls(StreamUrls streamUrls) {
        this.streamUrls = streamUrls;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
