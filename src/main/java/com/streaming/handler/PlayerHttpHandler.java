package com.streaming.handler;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class PlayerHttpHandler extends HttpHandler {
    @Override
    public void service(Request request, Response response) throws Exception {
        response.getWriter().write("hello");
    }
}
