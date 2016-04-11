package vccorp.project.cnnd.vtvnews.main.model;

/**
 * Created by Admin on 4/5/2016.
 */
public class LichChieuModel {
    private String Title;
    private String description;
    private String avatar;
    private String showOnSchedule;
    private String scheduleTime;
    private String channelId;
    private String zoneVideoId;
    private String zoneName;
    private String Url;
    private String urlShare;
    private String thumb;
    private String endTime;

    public LichChieuModel() {

    }

    public LichChieuModel(String Title, String description, String avatar, String showOnSchedule,
                          String scheduleTime, String channelId, String zoneVideoId, String zoneName,
                          String Url, String urlShare, String thumb, String endTime) {
        this.Title = Title;
        this.description = description;
        this.avatar = avatar;
        this.showOnSchedule = showOnSchedule;
        this.scheduleTime = scheduleTime;
        this.channelId = channelId;
        this.zoneVideoId = zoneVideoId;
        this.zoneName = zoneName;
        this.Url = Url;
        this.urlShare = urlShare;
        this.thumb = thumb;
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public String getShowOnSchedule() {
        return showOnSchedule;
    }

    public void setShowOnSchedule(String showOnSchedule) {
        this.showOnSchedule = showOnSchedule;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getZoneVideoId() {
        return zoneVideoId;
    }

    public void setZoneVideoId(String zoneVideoId) {
        this.zoneVideoId = zoneVideoId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUrlShare() {
        return urlShare;
    }

    public void setUrlShare(String urlShare) {
        this.urlShare = urlShare;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
