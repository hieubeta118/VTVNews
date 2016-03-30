package vccorp.project.cnnd.vtvnews.main.helpers;

import org.json.JSONObject;

import vccorp.project.cnnd.vtvnews.main.model.ListNewsTopic;

/**
 * Created by Admin on 3/25/2016.
 */
public class ParseListNewsTopic {
    public static final String TAG_ID = "id";
    public static final String TAG_TITLE = "title";
    public static final String TAG_CATE_URL = "cateUrl";
    public static ListNewsTopic parse(JSONObject jsonObject, ListNewsTopic listNewsTopic){
        listNewsTopic.setCateId(jsonObject.optString(TAG_ID));
        listNewsTopic.setCateTitle(jsonObject.optString(TAG_TITLE));
        listNewsTopic.setCategoryUrl(jsonObject.optString(TAG_CATE_URL));
        return listNewsTopic;
    }
}
