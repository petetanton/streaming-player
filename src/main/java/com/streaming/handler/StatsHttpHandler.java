package com.streaming.handler;

import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.streaming.PlayerException;
import com.streaming.domain.stats.ContainerBitrateEvent;
import com.streaming.domain.stats.PlayerStat;
import com.streaming.dynamo.DynamoDao;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.InputStream;

public class StatsHttpHandler extends HttpHandler {
    private static final Logger LOG = Logger.getLogger(StatsHttpHandler.class);
    private static final String EVENT_TYPE = "Event-Type";
    private static final String PLAYER_UUID = "Player-UUID";
    private static final String STREAM_ID = "Stream-Id";

    private final DynamoDao dao;

    public StatsHttpHandler(final DynamoDao dao) {
        this.dao = dao;
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        if (!request.getMethod().equals(Method.POST)) {
            response.sendError(405, "Method not allowed");
            return;
        }

        if (StringUtils.isNullOrEmpty(request.getHeader(STREAM_ID))) {
            response.sendError(400, "No stream ID was set in the request");
            return;
        }
        final InputStream is = request.getInputStream();

        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.registerModule(new JodaModule());

            if (request.getHeader(EVENT_TYPE).equalsIgnoreCase("CONTAINER_BITRATE")) {
                final ContainerBitrateEvent containerBitrateEvent;
                containerBitrateEvent = mapper.readValue(is, ContainerBitrateEvent.class);

                LOG.info(containerBitrateEvent.getBitrate());

                final PlayerStat playerStat = new PlayerStat();
                playerStat.setPlayerUUID(request.getHeader(PLAYER_UUID));
                playerStat.setStreamId(request.getHeader(STREAM_ID));
                playerStat.setContainerBitrateEvent(containerBitrateEvent);
                playerStat.setRendition(Integer.toString(containerBitrateEvent.getBitrate()));
                playerStat.setUpdateTimestamp(System.currentTimeMillis());

                dao.savePlayerStat(playerStat);

            } else {
                response.sendError(400, "Unknown event type");
                return;
            }
        } catch (Exception e) {
            LOG.error(e);
            throw new PlayerException("An issue occurred whilst parsing the request", e);
        }

        is.close();

        response.sendAcknowledgement();
    }
}
