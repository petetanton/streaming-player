package com.streaming.handler;


import com.streaming.domain.stats.PlayerStat;
import com.streaming.dynamo.DynamoDao;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatsHttpHandlerTest {
    private static final String EVENT_TYPE = "Event-Type";
    private static final String PLAYER_UUID = "Player-UUID";
    private static final String STREAM_ID = "Stream-Id";

    private StatsHttpHandler underTest;

    @Mock
    private Response response;
    @Mock
    private Request request;

    @Mock
    private DynamoDao dao;

    @Before
    public void setup() throws FileNotFoundException {
        underTest = new StatsHttpHandler(dao);


        InputStream is = new FileInputStream("src/test/resources/stats_payload.json");
        when(request.getMethod()).thenReturn(Method.POST);
        when(request.getInputStream()).thenReturn(is);
    }

    @Test
    public void itReturnsAnErrorOnAGetRequest() throws Exception {
        when(request.getMethod()).thenReturn(Method.GET);

        underTest.service(request, response);

        verify(request).getMethod();
        verify(response).sendError(405, "Method not allowed");
        verifyNoMore();
    }

    @Test
    public void itReturnsAnErrorIfStreamIdHeaderIsMissing() throws Exception {
        when(request.getHeader(STREAM_ID)).thenReturn(null);

        underTest.service(request, response);

        verify(request).getMethod();
        verify(request).getHeader(STREAM_ID);
        verify(response).sendError(400, "No stream ID was set in the request");
        verifyNoMore();
    }

    @Test
    public void itReturnsAnErrorIfUnknownEventType() throws Exception {
        when(request.getHeader(EVENT_TYPE)).thenReturn("balh-blah");
        when(request.getHeader(STREAM_ID)).thenReturn("4");

        underTest.service(request, response);

        verify(request).getMethod();
        verify(request).getHeader(STREAM_ID);
        verify(request).getHeader(EVENT_TYPE);
        verify(response).sendError(400, "Unknown event type");
        verify(request).getInputStream();
        verifyNoMore();
    }

    @Test
    public void itBuildsAnObjectFromRequest() throws Exception {
        when(request.getHeader(EVENT_TYPE)).thenReturn("CONTAINER_BITRATE");
        when(request.getHeader(STREAM_ID)).thenReturn("4");

        underTest.service(request, response);

        verify(dao).savePlayerStat(any(PlayerStat.class));

        verify(request).getMethod();

        verify(request).getInputStream();
        verify(request).getHeader(EVENT_TYPE);
        verify(request).getHeader(PLAYER_UUID);
        verify(request, times(2)).getHeader(STREAM_ID);
        verify(response).sendAcknowledgement();
        verifyNoMore();
    }

    private void verifyNoMore() {
        verifyNoMoreInteractions(response, request, dao);

    }
}