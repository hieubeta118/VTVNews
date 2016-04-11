package vccorp.project.cnnd.vtvnews.main.model;

import java.io.Serializable;

/**
 * Created by Admin on 4/6/2016.
 */
public class VideoRelatedModel implements Serializable {
    private String videoId;
    private String ZoneId;
    private String videoName;
    private String description;
    private String avatar;
    private String dateSchedule;
    private String fileName;
    private String thumbImage;
    private String shareUrl;

    public VideoRelatedModel(){

    }
    public VideoRelatedModel(String videoId, String ZoneId, String videoName,
                             String description, String avatar, String dateSchedule,
                             String fileName, String thumbImage, String shareUrl){
        this.videoId = videoId;
        this.ZoneId = ZoneId;
        this.videoName = videoName;
        this.description = description;
        this.avatar = avatar;
        this.dateSchedule = dateSchedule;
        this.fileName = fileName;
        this.thumbImage = thumbImage;
        this.shareUrl = shareUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getZoneId() {
        return ZoneId;
    }

    public void setZoneId(String zoneId) {
        ZoneId = zoneId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDateSchedule() {
        return dateSchedule;
    }

    public void setDateSchedule(String dateSchedule) {
        this.dateSchedule = dateSchedule;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
