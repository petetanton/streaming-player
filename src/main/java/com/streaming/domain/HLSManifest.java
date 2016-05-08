package com.streaming.domain;

import java.util.ArrayList;
import java.util.List;

public class HLSManifest {

    private int targetDuraton;
    private int mediaSequence;
    private List<HLSStreamInf> hlsStreamInf;

    public static HLSManifest generateHLSManifest(String input) throws AssertionError {
        final String[] split = input.split("\n");

        assert split[0].equals("#EXTM3U");
        assert split[1].equals("#EXT-X-VERSION:3");
        assert split[4].contains("EXT-X-STREAM-INF:PROGRAM");
        assert split[split.length - 1].equals("#EXT-X-ENDLIST");

        final HLSManifest manifest = new HLSManifest();

        manifest.setTargetDuraton(Integer.parseInt(split[2].substring(split[2].indexOf(":") + 1)));
        manifest.setMediaSequence(Integer.parseInt(split[3].substring(split[3].indexOf(":") + 1)));

        List<HLSStreamInf> hlsStreamInfs = new ArrayList<>();
        int noOfRepresentations = (split.length - 4) / 2;

        for (int i = 0; i < noOfRepresentations; i++) {

            hlsStreamInfs.add(new HLSStreamInf(split[4 + (2 * i)], split[5 + (2 * i)]));
        }

        manifest.setHlsStreamInfs(hlsStreamInfs);
        System.out.println(noOfRepresentations);

        return manifest;
    }

    private static String getSubString(String input, String endChar) {
        return input.substring(0, input.indexOf(endChar));
    }

    private static int getSubStringAsInt(String input, String endChar) {
        return Integer.parseInt(input.substring(0, input.indexOf(endChar)));
    }

    public List<HLSStreamInf> getHlsStreamInf() {
        return hlsStreamInf;
    }

    public void setHlsStreamInfs(List<HLSStreamInf> hlsStreamInf) {
        this.hlsStreamInf = hlsStreamInf;
    }

    public int getMediaSequence() {
        return mediaSequence;
    }

    public void setMediaSequence(int mediaSequence) {
        this.mediaSequence = mediaSequence;
    }

    public int getTargetDuraton() {
        return targetDuraton;
    }

    public void setTargetDuraton(int targetDuraton) {
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
