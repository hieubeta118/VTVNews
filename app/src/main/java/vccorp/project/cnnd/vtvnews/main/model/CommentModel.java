package vccorp.project.cnnd.vtvnews.main.model;

import android.net.Uri;

/**
 * Created by Admin on 4/2/2016.
 */
public class CommentModel {
    private String newsUrl;
    private String newsTitleFull;
    private String commentUrl;

    public CommentModel() {

    }

    public CommentModel(String newsUrl, String newsTitleFull, String commentUrl) {
        this.newsUrl = newsUrl;
        this.newsTitleFull = newsTitleFull;
        this.commentUrl = commentUrl;

    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setUrlNews(String newsUrl) {
        this.newsUrl = newsUrl;
    }


    public String getNewsTitleFull() {
        return newsTitleFull;
    }

    public void setNewsTitleFull(String newsTitleFull) {
        this.newsTitleFull = newsTitleFull;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }
}
