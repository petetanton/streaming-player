package com.streaming.player;

public class PlayerJSGenerator {


    public static String generateStatsScript(int streamId) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<script>\n")
                .append("function guid() {\n" +
                        "  function s4() {\n" +
                        "    return Math.floor((1 + Math.random()) * 0x10000)\n" +
                        "      .toString(16)\n" +
                        "      .substring(1);\n" +
                        "  }\n" +
                        "  return s4() + s4() + '-' + s4() + '-' + s4() + '-' +\n" +
                        "    s4() + '-' + s4() + s4() + s4();\n" +
                        "}\n")

                .append("var session = guid();\n")
                .append("\nplayer.listenTo(player.core.getCurrentContainer(), Clappr.Events.CONTAINER_BITRATE, onBitrateChanged)\n")

                .append("function onBitrateChanged(rendition) {\n")
                .append("\tconsole.log(rendition.height);\n")
                .append("\tconsole.log(rendition.width);\n")
                .append("\tconsole.log(rendition.bitrate);\n")
                .append("\tconsole.log(rendition.level);\n")
                .append("\tpostDataToBackend('stats', rendition, 'CONTAINER_BITRATE', '").append(streamId).append("');\n")
                .append("}\n\n\n")

                .append("function postDataToBackend(url, data, eventType, streamId) {\n")
                .append("\tvar xhr = new XMLHttpRequest();\n")
                .append("\txhr.open('POST', url, true);\n")
                .append("\txhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');\n")
                .append("\txhr.setRequestHeader('Player-UUID', session);\n")
                .append("\txhr.setRequestHeader('Event-Type', eventType);\n")
                .append("\txhr.setRequestHeader('Stream-Id', streamId);\n")
                .append("\n")
                .append("\t// send the collected data as JSON\n")
                .append("\txhr.send(JSON.stringify(data));\n")
                .append("\n")
                .append("\txhr.onloadend = function () {\n")
                .append("\t\tconsole.log('finished posting data');\n")
                .append("\t}}\n\n")


                .append("console.log(guid());\n")
                .append("</script>\n");
        return sb.toString();
    }


}
