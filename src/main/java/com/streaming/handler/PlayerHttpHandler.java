package com.streaming.handler;

import com.amazonaws.util.StringUtils;
import com.streaming.CacheControl;
import com.streaming.SRApiClient;
import com.streaming.domain.Stream;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PlayerHttpHandler extends HttpHandler {

    @Override
    public void service(Request request, Response response) throws Exception {
        response.setHeader("Content-Type", "text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>");
        sb.append("<script type=\"text/javascript\" src=\"https://s3-eu-west-1.amazonaws.com/sr-static-hosting/player/src/clappr/0-2-49/clappr.min.js\"></script>");
        sb.append("<script type=\"text/javascript\" src=\"https://s3-eu-west-1.amazonaws.com/sr-static-hosting/player/src/clappr-level-selector/0-1-10/level-selector.min.js\"></script>");
        sb.append("</head>");
        sb.append("<body>");

        final String streamId = request.getParameter("id");
        final Stream stream;
        final String streamManifestUrl;


        if (StringUtils.isNullOrEmpty(streamId)) {
            sendError(response, sb, "Please request player with a stream id set", HttpStatus.BAD_REQUEST_400);
            return;
        }

        try {
            stream = SRApiClient.getStream(Integer.parseInt(streamId));
        } catch (NumberFormatException e) {
            sendError(response, sb, "Please set id to a valid number", HttpStatus.BAD_REQUEST_400);
            return;
        }

        if (stream == null) {
            sendError(response, sb, "Stream does not exist", HttpStatus.NOT_FOUND_404);
            return;
        }

        try {
            streamManifestUrl = stream.getStreamUrls().getStreamTypes().get("hls").getStreamBitrates().get("adaptive").getUrl();
        } catch (NullPointerException e) {
            sendError(response, sb, "HLS adaptive does not exist", HttpStatus.NOT_FOUND_404);
            return;
        }

        sb.append("<div id=\"player\"></div>");
        sb.append("<script>");
        sb.append("var player = new Clappr.Player({source: \"");
        sb.append(streamManifestUrl);
        sb.append("\", maxBufferLength:240, useDvrControls: true, parentId: \"#player\", gaAccount: 'UA-73120346-1',\n" +
                "    gaTrackerName: 'streamingrocket-player-");
        sb.append(stream.getStreamId());
        sb.append("', plugins: {");
        sb.append("'core': [LevelSelector]");
        sb.append("}});");
        sb.append("</script>");
        sb.append("<a href=\"");
        sb.append(streamManifestUrl);
        sb.append("\">");
        sb.append(streamManifestUrl);
        sb.append("</a>");
        sb.append("<script>\n" +
                "\n" +
                "\n" +
                "    (function (i, s, o, g, r, a, m) {\n" +
                "        i['GoogleAnalyticsObject'] = r;\n" +
                "        i[r] = i[r] || function () {\n" +
                "            (i[r].q = i[r].q || []).push(arguments)\n" +
                "        }, i[r].l = 1 * new Date();\n" +
                "        a = s.createElement(o),\n" +
                "            m = s.getElementsByTagName(o)[0];\n" +
                "        a.async = 1;\n" +
                "        a.src = g;\n" +
                "        m.parentNode.insertBefore(a, m)\n" +
                "    })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');\n" +
                "\n" +
                "    ga('create', 'UA-73120346-1', 'auto');\n" +
                "    ga('send', 'pageview');\n" +
                "\n" +
                "</script>");

        sb.append("</body>");
        sb.append("</html>");

        response.setHeader("Cache-Control", CacheControl.calculateCacheHeader(stream));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(sb.toString());
    }

    private void sendError(Response response, StringBuilder sb, String message, HttpStatus status) throws IOException {
        sb.append("<strong>").append(message).append("</strong>");
        sb.append("</body>");
        sb.append("</html>");
        response.getWriter().write(sb.toString());
        response.setStatus(status);
    }
}
