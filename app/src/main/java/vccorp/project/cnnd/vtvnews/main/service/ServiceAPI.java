package vccorp.project.cnnd.vtvnews.main.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by Admin on 3/24/2016.
 */
public interface ServiceAPI {
    /**
     * @GET list live video item
     * @param callback
     */
    @GET(ServiceConfig.LIST_CHANNEL)
    void getListLiveVideoItem(Callback<JsonArray> callback);

    /**
     * @GET List source link
     * @param callback
     */
    @GET(ServiceConfig.SOURCE_LINKS)
    void getSourceLink(Callback<JsonObject> callback);

    /**
     * @GET Comment Api
     * @param newsUrl
     * @param callback
     */
    @GET(ServiceConfig.COMMENT_URL + "&"
            + ServiceConfig.NEWS_URL)
    void getCommentUrl(@Query("newsurl") String newsUrl, Callback<JsonObject> callback);

    /**
     * @GET Demo lich chieu
     * @param channelName
     * @param callback
     */
    @GET(ServiceConfig.DEMO_LICH)
    void getDemoLich(@Query("channel") String channelName, Callback<JsonArray> callback);

    /**
     * @GET video related
     * @param channelName
     * @param callback
     */
    @GET("/api/app.ashx?config=video_newest&pageindex=1&pagesize=300")
    void getVideoRelated(@Query("channel") String channelName, Callback<JsonArray> callback);
//        @GET("/api/app.ashx?config=video_newest&channel=vtv1&pageindex=1&pagesize=300")
//    void getVideoRelated(Callback<JsonArray> callback);

    /**
     * @GET Search video related
     * @param param
     * @param channelName
     * @param callback
     */
    @POST("/api/app.ashx?config=video_search&pageindex=1&pagesize=50&keyword=")
    void getSearchVideo(@QueryMap Map<String, String> param,
                        @Query("channel") String channelName,
                        Callback<JsonArray> callback);

    /**
     * @GET cache Url
     * @param callback
     */
    @GET("/api/app.ashx?config=listNews")
    void getCacheUrl(Callback<JsonArray> callback);
}
