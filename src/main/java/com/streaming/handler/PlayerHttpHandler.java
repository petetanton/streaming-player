package com.streaming.handler;

import com.streaming.SRApiClient;
import com.streaming.domain.Stream;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class PlayerHttpHandler extends HttpHandler {
    private static final String UTF_8 = "UTF-8";

    @Override
    public void service(Request request, Response response) throws Exception {
        final Stream stream = SRApiClient.getStream(4);


        final String src = stream.getStreamUrls().getStreamTypes().get("hls").getStreamBitrates().get("adaptive").getUrl();
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>");
        sb.append("<script type=\"text/javascript\" src=\"https://s3-eu-west-1.amazonaws.com/sr-static-hosting/player/src/clappr/0-2-49/clappr.min.js\"></script>");
        sb.append("<script type=\"text/javascript\" src=\"https://s3-eu-west-1.amazonaws.com/sr-static-hosting/player/src/clappr-level-selector/0-1-10/level-selector.min.js\"></script>");
        sb.append("</head>");


        sb.append("<body>");
        sb.append("<div id=\"player\"></div>");
        sb.append("<script>");
        sb.append("      var player = new Clappr.Player({source: \"");
        sb.append(src);
        sb.append("\", maxBufferLength:240, useDvrControls: true, parentId: \"#player\", plugins: {");
        sb.append("'core': [LevelSelector]");
        sb.append("}});");
        sb.append("</script>");
        sb.append("<a href=\"");
        sb.append(src);
        sb.append("\">");
        sb.append(src);
        sb.append("</a>");
        sb.append("</body>");
        sb.append("</html>");


        response.setHeader("Content-Type", "text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(sb.toString());
    }
}
