package vccorp.project.cnnd.vtvnews.main.model;

/**
 * Created by Admin on 3/25/2016.
 */
public class ListLiveVideoModel {
    private String linkFile;
    private String nameChannel;
    private String thumbImage;
    private String linkShare;

    public ListLiveVideoModel(){

    }

    public ListLiveVideoModel(String linkFile, String nameChannel,
                              String thumbImage, String linkShare){
        this.linkFile = linkFile;
        this.nameChannel = nameChannel;
        this.thumbImage = thumbImage;
        this.linkShare = linkShare;
    }

    public String getLinkShare() {
        return linkShare;
    }

    public void setLinkShare(String linkShare) {
        this.linkShare = linkShare;
    }

    public String getLinkFile() {
        return linkFile;
    }

    public void setLinkFile(String linkFile) {
        this.linkFile = linkFile;
    }

    public String getNameChannel() {
        return nameChannel;
    }

    public void setNameChannel(String nameChannel) {
        this.nameChannel = nameChannel;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }
}
