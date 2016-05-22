package com.streaming;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.streaming.dynamo.DynamoDao;
import com.streaming.handler.PlayerHttpHandler;
import com.streaming.handler.StatsHttpHandler;
import com.streaming.http.HttpUtils;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import java.util.concurrent.CountDownLatch;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);

    private Main() {
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

        final AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient();
        amazonDynamoDBClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
        final DynamoDao dao = new DynamoDao(amazonDynamoDBClient);

        HttpServer server = HttpServer.createSimpleServer();
        try {
            server.getServerConfiguration().addHttpHandler(new PlayerHttpHandler(HttpUtils.buildClient(), HttpUtils.buildClient()), "/player");
            server.getServerConfiguration().addHttpHandler(new StatsHttpHandler(dao), "/stats");
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
