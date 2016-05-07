package com.streaming;

import org.kohsuke.args4j.Option;

public class Args {

    @Option(name = "--api", metaVar = "<URI>", usage = "hostname to platform api") private String platformApi;

    public String getPlatformApi() {
        return platformApi;
    }
}
