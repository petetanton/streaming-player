package com.streaming.domain;

public class StreamTypes {

    private String url;
    private Integer bitrate;
    private Boolean isAdaptive;

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public Boolean isAdaptive() {
        return isAdaptive;
    }

    public void setIsAdaptive(Boolean isAdaptive) {
        this.isAdaptive = isAdaptive;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
