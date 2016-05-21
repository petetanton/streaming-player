package com.streaming.domain.hls;

public class HLSStreamInf {

    private int programId;
    private int bandwidth;
    private int width;
    private int height;
    private String manifest;

    public HLSStreamInf(String line1, String line2) {
        final String[] split = line1.split("=");

        this.programId = Integer.parseInt(split[1].substring(0, split[1].indexOf(',')));
        this.bandwidth = Integer.parseInt(split[2].substring(0, split[2].indexOf(',')));
        this.width = Integer.parseInt(split[3].substring(0, split[3].indexOf('x')));
        this.height = Integer.parseInt(split[3].substring(split[3].indexOf('x') + 1));
        this.manifest = line2;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getManifest() {
        return manifest;
    }

    public void setManifest(String manifest) {
        this.manifest = manifest;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
