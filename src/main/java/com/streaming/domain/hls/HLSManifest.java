package com.streaming.domain.hls;

import java.util.ArrayList;
import java.util.List;

public class HLSManifest {

    private int targetDuraton;
    private int mediaSequence;
    private List<HLSStreamInf> hlsStreamInf;

    public static HLSManifest generateHLSManifest(String input) throws AssertionError {
        final String[] split = input.split("\n");

        if (!"#EXTM3U".equals(split[0]) || !"#EXT-X-VERSION:3".equals(split[1]) || !split[4].contains("EXT-X-STREAM-INF:PROGRAM") || !"#EXT-X-ENDLIST".equals(split[split.length - 1])) {
            throw new AssertionError("There was an issue with the HLS manifest");
        }

        final HLSManifest manifest = new HLSManifest();

        manifest.setTargetDuraton(Integer.parseInt(split[2].substring(split[2].indexOf(':') + 1)));
        manifest.setMediaSequence(Integer.parseInt(split[3].substring(split[3].indexOf(':') + 1)));

        List<HLSStreamInf> hlsStreamInfs = new ArrayList<>();
        int noOfRepresentations = (split.length - 4) / 2;

        for (int i = 0; i < noOfRepresentations; i++) {

            hlsStreamInfs.add(new HLSStreamInf(split[4 + (2 * i)], split[5 + (2 * i)]));
        }

        manifest.setHlsStreamInfs(hlsStreamInfs);

        return manifest;
    }

    public List<HLSStreamInf> getHlsStreamInf() {
        return hlsStreamInf;
    }

    private void setHlsStreamInfs(List<HLSStreamInf> hlsStreamInf) {
        this.hlsStreamInf = hlsStreamInf;
    }

    public int getMediaSequence() {
        return mediaSequence;
    }

    private void setMediaSequence(int mediaSequence) {
        this.mediaSequence = mediaSequence;
    }

    public int getTargetDuraton() {
        return targetDuraton;
    }

    private void setTargetDuraton(int targetDuraton) {
        this.targetDuraton = targetDuraton;
    }

    public String generateClapprSelectorConfig() {
        StringBuilder sb = new StringBuilder();

        this.getHlsStreamInf().sort(new HLSStreamInfComparator());

        sb.append("levelSelectorConfig: {\n");
        sb.append("\ttitle: 'Quality',\n");
        sb.append("\tlabels: {\n");

        int i = 0;
        for (HLSStreamInf streamInf : this.getHlsStreamInf()) {
            sb.append("\t\t").append(i).append(": '").append(streamInf.getHeight()).append("p',\t//").append(streamInf.getBandwidth()).append("\n");
            i++;
        }
        sb.append("\t}\n");
        sb.append("},\n");

        return sb.toString();
    }
}
