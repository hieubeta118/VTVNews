package vccorp.project.cnnd.vtvnews.main.service;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Admin on 3/24/2016.
 */
public interface ServiceAPI {
    /**
     * @GET list live video item
     *
     */
    @GET(ServiceConfig.LIST_CHANNEL)
    void getListLiveVideoItem(Callback<JSONArray> callback);
    /**
     * @GET List source link
     *
     */
    @GET(ServiceConfig.SOURCE_LINKS)
    void getSourceLink(Callback<JsonObject> callback);

}
