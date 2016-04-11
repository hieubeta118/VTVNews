package vccorp.project.cnnd.vtvnews.main.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Admin on 3/23/2016.
 */
public enum AppPreferences {
    INSTANCE;
    /**
     * Preference save newsUrl to share
     */
    public static final String GET_SHARE_URL = "newsUrl";
    public static final String GET_NEWS_TITLE = "newsTitleFull";
    public static final String GET_CHANNEL_NAME = "channelName";
    public static final String GET_VIDEO_URI = "videoUri";

    private SharedPreferences pref;

    public static void init(Context context){
        INSTANCE.pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean setShareUrl(String shareUrl){
        return pref.edit().putString(GET_SHARE_URL, shareUrl).commit();
    }
    public String getShareUrl(){
        return pref.getString(GET_SHARE_URL, null);
    }

    public boolean setNewsTitles(String newsTitle){
        return pref.edit().putString(GET_NEWS_TITLE, newsTitle).commit();
    }

    public String getNewsTitle(){
        return pref.getString(GET_NEWS_TITLE, null);
    }

    public boolean setChannelName(String channelName){
        return pref.edit().putString(GET_CHANNEL_NAME, channelName).commit();
    }
    public String getChannelName(){
        return pref.getString(GET_CHANNEL_NAME, null);
    }
    public boolean setVideoURI(String videoURI){
        return pref.edit().putString(GET_VIDEO_URI, videoURI).commit();
    }
    public String getVideoURI(){
        return pref.getString(GET_VIDEO_URI, null);
    }
}
