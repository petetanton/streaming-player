package com.streaming.domain;

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

    public String getDescription() {
        return description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
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

}
