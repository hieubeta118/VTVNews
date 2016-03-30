package vccorp.project.cnnd.vtvnews.main.model;

/**
 * Created by Admin on 3/25/2016.
 */
public class ListNewsTopic {
    private String newsId;
    private String tinTucUrl;
    private String videoUrl;
    private String mainPageUrl;
    private String categoryUrl;
    private String cateId;
    private String cateTitle;
    public ListNewsTopic(){

    }
    public ListNewsTopic(String newsId, String tinTucUrl, String videoUrl,
                         String mainPageUrl, String categoryUrl, String cateId,
                         String cateTitle){
        this.newsId = newsId;
        this.tinTucUrl = tinTucUrl;
        this.videoUrl = videoUrl;
        this.mainPageUrl = mainPageUrl;
        this.categoryUrl = categoryUrl;
        this.cateId = cateId;
        this.cateTitle = cateTitle;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTinTucUrl() {
        return tinTucUrl;
    }

    public void setTinTucUrl(String tinTucUrl) {
        this.tinTucUrl = tinTucUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getMainPageUrl() {
        return mainPageUrl;
    }

    public void setMainPageUrl(String mainPageUrl) {
        this.mainPageUrl = mainPageUrl;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateTitle() {
        return cateTitle;
    }

    public void setCateTitle(String cateTitle) {
        this.cateTitle = cateTitle;
    }
}
