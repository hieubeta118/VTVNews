package vccorp.project.cnnd.vtvnews.main.helpers;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import vccorp.project.cnnd.vtvnews.main.model.VideoRelatedModel;

/**
 * Created by Admin on 4/6/2016.
 */
public class ParseListVideoRelated {
    public static VideoRelatedModel parse(JSONObject jsonObject, VideoRelatedModel videoRelatedModel){
        videoRelatedModel.setVideoId(jsonObject.optString("Id"));
        videoRelatedModel.setZoneId(jsonObject.optString("ZoneId"));
        videoRelatedModel.setVideoName(jsonObject.optString("Name"));
        Log.i("getName", jsonObject.optString("Name"));
        videoRelatedModel.setDescription(jsonObject.optString("Description"));
        videoRelatedModel.setAvatar(jsonObject.optString("Avatar"));
        videoRelatedModel.setDateSchedule(convertDate(jsonObject.optString("DistributionDate")));
        videoRelatedModel.setFileName(jsonObject.optString("FileName"));
        videoRelatedModel.setThumbImage(jsonObject.optString("thumb"));
        videoRelatedModel.setShareUrl(jsonObject.optString("share_url"));
        return videoRelatedModel;
    }
    private static String convertDate(String date) {
        try {
            SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            apiFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date d = apiFormat.parse(date);
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return parseFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
