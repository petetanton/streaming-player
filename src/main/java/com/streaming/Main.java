package com.streaming;

import com.streaming.handler.PlayerHttpHandler;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.util.concurrent.CountDownLatch;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);


    public static void main(String[] inputArgs) {
        final Args args = new Args();
        CmdLineParser cmdLineParser = new CmdLineParser(args);
        try {
            cmdLineParser.parseArgument(inputArgs);
            start(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }

    }

    private static void start(Args args) {
        System.out.println(args.getPlatformApi());
        HttpServer server = HttpServer.createSimpleServer();
        server.getServerConfiguration().addHttpHandler(new PlayerHttpHandler(args), "/player");

        try {
            server.start();
            LOG.info("ready...");
            new CountDownLatch(1).await();
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
