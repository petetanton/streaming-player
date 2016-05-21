package com.streaming.handler;

import com.amazonaws.util.StringUtils;
import com.streaming.CacheControl;
import com.streaming.HLSManifestClient;
import com.streaming.PlayerException;
import com.streaming.SRApiClient;
import com.streaming.domain.hls.HLSManifest;
import com.streaming.domain.Stream;
import com.streaming.http.HttpUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PlayerHttpHandler extends HttpHandler {

    private static final Logger LOG = Logger.getLogger(PlayerHttpHandler.class);
    @Override
    public void service(Request request, Response response) throws Exception {
        response.setHeader("Content-Type", "text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n" +
                "<!--[if lt IE 7]>      <html lang=\"en\" class=\"no-js lt-ie9 lt-ie8 lt-ie7\"> <![endif]-->\n" +
                "<!--[if IE 7]>         <html lang=\"en\" class=\"no-js lt-ie9 lt-ie8 ie7\"> <![endif]-->\n" +
                "<!--[if IE 8]>         <html lang=\"en\" class=\"no-js lt-ie9 ie8\"> <![endif]-->\n" +
                "<!--[if IE 9]>         <html lang=\"en\" class=\"no-js lt-ie10 ie9\"> <![endif]-->\n" +
                "<!--[if !IE]><!--> <html lang=\"en\" class=\"no-js\">             <!--<![endif]-->\n");
        sb.append("<head>\n");
        sb.append("<meta charset=\"utf-8\">\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
        sb.append("<script type=\"text/javascript\" src=\"https://s3-eu-west-1.amazonaws.com/sr-static-hosting/player/src/clappr/0-2-49/clappr.min.js\"></script>\n");
        sb.append("<script type=\"text/javascript\" src=\"https://s3-eu-west-1.amazonaws.com/sr-static-hosting/player/src/clappr-level-selector/0-1-10/level-selector.min.js\"></script>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");

        final String streamId = request.getParameter("id");
        final Stream stream;
        final String streamManifestUrl;


        if (StringUtils.isNullOrEmpty(streamId)) {
            sendError(response, sb, "Please request player with a stream id set", HttpStatus.BAD_REQUEST_400);
            return;
        }

        try {
            stream = SRApiClient.getStream(Integer.parseInt(streamId), HttpClients.custom().build());
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



        sb.append("<div id=\"player\" style=\"width:640px; height=360px;\"></div>\n");
        sb.append("<script>\n");
        sb.append("var player = new Clappr.Player({source: \"");
        sb.append(streamManifestUrl);
        sb.append("\", maxBufferLength:240, useDvrControls: true, parentId: \"#player\", mediacontrol: {seekbar: \"#66B2FF\", buttons: \"#ffffff\"}, gaAccount: 'UA-73120346-1',\n" +
                "    gaTrackerName: 'streamingrocket-player-");
        sb.append(stream.getStreamId());
        sb.append("', plugins: {");
        sb.append("'core': [LevelSelector]");
        sb.append("},");

        final HLSManifest hlsManifest;
        try {
            hlsManifest = HLSManifestClient.getHLSManifest(streamManifestUrl, HttpUtils.buildClient());
            sb.append(hlsManifest.generateClapprSelectorConfig());
        } catch (PlayerException e) {
            LOG.error("Could not parse the Manifest", e);
        }

        sb.append("});");
        sb.append("</script>");
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
                "</script>\n");

        sb.append("<script>\nfunction resizePlayer(){\n" +
                "\tvar aspectRatio = 9/16;\n" +
                "\tnewWidth = document.getElementById('player').parentElement.offsetWidth;\n" +
                "\tnewHeight = 2 * Math.round(newWidth * aspectRatio/2);\n" +
                "\tplayer.resize({width: newWidth, height: newHeight});\n" +
                "}\n" +
                "\n" +
                "resizePlayer();\n" +
                "window.onresize = resizePlayer;\n</script>\n");

        sb.append("</body>");
        sb.append("</html>");

        response.setHeader("Cache-Control", CacheControl.getDefaultCacheHeader());
        response.setHeader("X-Manifest-Location", streamManifestUrl);
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
