package com.streaming;

import com.streaming.handler.PlayerHttpHandler;
import com.streaming.http.HttpUtils;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import java.util.concurrent.CountDownLatch;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);

    Main() {
    }

    public static void main(String[] inputArgs) {
        try {
            start();
        } catch (Exception e) {
            LOG.error(e);
            System.exit(2);
        }

    }

    private static void start() {
        HttpServer server = HttpServer.createSimpleServer();
        try {
            server.getServerConfiguration().addHttpHandler(new PlayerHttpHandler(HttpUtils.buildClient(), HttpUtils.buildClient()), "/player");
        } catch (PlayerException e) {
            LOG.error("Failed to add PlayerHttpHandler", e);
        }

        try {
            server.start();
            LOG.info("ready...");
            new CountDownLatch(1).await();
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
