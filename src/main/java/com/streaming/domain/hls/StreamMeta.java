package com.streaming.domain.hls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Date;

public class StreamMeta {
    private String name;                //required
    private String description;         //required
    private String thumbnailUrl;        //required
    private Date uploadDate;            //optional
    private int duration;               //optional
    private String contentUrl;          //no supported by google for streaming
    private String embedUrl;            //optional
    private int interactionCount;       //optional

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getDescription() throws Exception {
        if (this.description == null) {
            throw new Exception("Description cannot be null");
        } else {
            return description;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        if (duration == 0)
            return null;
        else
            return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationAsISOString() {
        if (getDuration() == null || getDuration() == 0) {
            return null;
        }
        String output = "PT";
        if (getDuration() > 59) {
            int mins = getDuration() / 60;
            int secs = getDuration() - mins * 60;
            output += mins + "M" + secs + "S";
        } else {
            output += "0M" + getDuration() + "S";
        }
        return output;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public int getInteractionCount() {
        return interactionCount;
    }

    public void setInteractionCount(int interactionCount) {
        this.interactionCount = interactionCount;
    }

    public String getName() throws Exception {
        if (this.name == null) {
            throw new Exception("Name cannot be null");
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() throws Exception {
        if (this.thumbnailUrl == null) {
            throw new Exception("ThumbnailUrl cannot be null");
        } else {
            return thumbnailUrl;
        }
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadDateAsISOString() {
        if (this.getUploadDate() == null) {
            return null;
        } else {
            DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
            return dateTimeFormatter.print(getUploadDate().getTime());
        }
    }

    @JsonProperty("@context")
    public String getContext() {
        return "http://schema.org";
    }

    @JsonProperty("@type")
    public String getType() {
        return "VideoObject";
    }

    public String buildJavascript() throws Exception {
        String output = "";//"{";
        output += "<script type=\"application/ld+json\">{";
        output += "\"@context\": \"http://schema.org\",";
        output += "\"@type\": \"VideoObject\",";
        output += "\"name\": \"" + this.getName() + "\",";
        output += "\"description\": \"" + this.getDescription() + "\",";
        output += "\"thumbnailUrl\": \"" + this.getThumbnailUrl() + "\"";
        if (this.getUploadDate() != null)
            output += ",\"uploadDate\": \"" + this.getUploadDateAsISOString() + "\"";
        if (this.getDuration() != null)
            output += ",\"duration\": \"" + this.getDurationAsISOString() + "\"";
        if (this.getContentUrl() != null && !this.getContentUrl().equals(""))
            output += ",\"contentUrl\": \"" + this.getContentUrl() + "\"";
        if (this.getEmbedUrl() != null && !this.getEmbedUrl().equals(""))
            output += ",\"embedUrl\": \"" + this.getEmbedUrl() + "\"";
        if (this.getInteractionCount() > 0)
            output += ",\"interactionCount\": \"" + this.getInteractionCount() + "\"";
        output += "}</script>";
        return output;
    }


    //<script type="application/ld+json">
//        {
//        "@context": "http://schema.org",
//        "@type": "VideoObject",
//        "name": "Title",  (required)
//        "description": "Video description",
//        "thumbnailUrl": "thumbnail.jpg",  (required)
//        "uploadDate": "2015-02-05T08:00:00+08:00",    (required)
//        "duration": "PT1M33S",
//        "contentUrl": "http://www.example.com/video123.flv",  (not supported for streaming)
//        "embedUrl": "http://www.example.com/videoplayer.swf?video=123",
//        "interactionCount": "2347"
//        }
//</script>
}
