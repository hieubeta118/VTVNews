package vccorp.project.cnnd.vtvnews.main.helpers;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import vccorp.project.cnnd.vtvnews.main.model.LichChieuModel;

/**
 * Created by Admin on 4/5/2016.
 */
public class ParseListLichChieu {
    public static LichChieuModel parse(JSONObject jsonObject, LichChieuModel lichChieuModel) {
        lichChieuModel.setTitle(jsonObject.optString("Title"));
        lichChieuModel.setDescription(jsonObject.optString("Description"));
        lichChieuModel.setAvatar(jsonObject.optString("Avatar"));
        lichChieuModel.setShowOnSchedule(jsonObject.optString("ShowOnSchedule"));
        lichChieuModel.setScheduleTime(convertDate(jsonObject.optString("ScheduleTime")));
        lichChieuModel.setChannelId(jsonObject.optString("ChannelId"));
        lichChieuModel.setZoneVideoId(jsonObject.optString("ZoneVideoId"));
        lichChieuModel.setZoneName(jsonObject.optString("ZoneName"));
        lichChieuModel.setUrl(jsonObject.optString("Url"));
        lichChieuModel.setUrlShare(jsonObject.optString("share_url"));
        lichChieuModel.setThumb(jsonObject.optString("thumb"));
        return lichChieuModel;
    }
    private static String convertDate(String date) {
        try {
            SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            apiFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date d = apiFormat.parse(date);
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
            return parseFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
