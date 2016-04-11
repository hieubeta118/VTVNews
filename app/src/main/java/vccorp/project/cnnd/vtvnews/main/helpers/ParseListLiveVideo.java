package vccorp.project.cnnd.vtvnews.main.helpers;

import org.json.JSONObject;

import vccorp.project.cnnd.vtvnews.main.model.ListLiveVideoModel;

/**
 * Created by Admin on 3/25/2016.
 */
public class ParseListLiveVideo {
    public static ListLiveVideoModel parse(JSONObject jsonObject, ListLiveVideoModel listLiveVideoModel) {
        listLiveVideoModel.setLinkFile(jsonObject.optString("link"));
        listLiveVideoModel.setNameChannel(jsonObject.optString("name"));
        listLiveVideoModel.setLinkShare(jsonObject.optString("share_url"));
        listLiveVideoModel.setThumbImage(jsonObject.optString("thumb"));
//        listLiveVideoModel.setIconChannel(jsonObject.optString("image"));
        return listLiveVideoModel;
    }
}
