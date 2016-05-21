package com.streaming.domain;

public class StreamBitrates {
    private int bitrate;
    private Boolean adaptive;
    private String url;

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public Boolean isAdaptive() {
        return adaptive;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAdaptive(Boolean adaptive) {
        this.adaptive = adaptive;
    }
}
