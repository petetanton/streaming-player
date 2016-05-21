package com.streaming.domain.hls;

public class StreamBitrates {
    private int bitrate;
    private Boolean adaptive;
    private String url;

    public StreamBitrates(int bitrate, Boolean adaptive, String url) {
        this.bitrate = bitrate;
        this.adaptive = adaptive;
        this.url = url;
    }

    public StreamBitrates(Boolean adaptive, String url) {
        this.adaptive = adaptive;
        this.url = url;
    }

    public StreamBitrates() {
    }

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
