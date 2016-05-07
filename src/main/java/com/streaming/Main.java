package com.streaming;

import com.streaming.handler.PlayerHttpHandler;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import java.util.concurrent.CountDownLatch;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);


    public static void main(String[] inputArgs) {
        try {
            start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }

    }

    private static void start() {
        HttpServer server = HttpServer.createSimpleServer();
        server.getServerConfiguration().addHttpHandler(new PlayerHttpHandler(), "/player");

        try {
            server.start();
            LOG.info("ready...");
            new CountDownLatch(1).await();
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
