package vccorp.project.cnnd.vtvnews.main.service;

/**
 * Created by Admin on 3/24/2016.
 */
public interface ServiceConfig {
    /**
     * Host domain of VTV
     */
    String SERVICE_HOST = "http://m.vtv.vn";

    /**-------------------------VIDEO & TV--------------------------------------------------------*/
    /**
     * Get List video following 4 parameters:
     *
     * @param channel from vtv1 -> vtv9
     * @param day
     * @param month
     * @param year
     */
    String LIST_VIDEO = "/api/app.ashx?config=livetv_schedule/";

    String CHANNEL = "channel=";

    String DAY = "&day=";

    String MONTH = "&month=";

    String YEAR = "&year=";

    String DEMO_LICH = "/api/app.ashx?config=livetv_schedule&day=11&month=4&year=2016";
    /**
     * List live Channel from VTV1 - > VTV 9
     */
    String LIST_CHANNEL = "/api/app.ashx?config=livetv";
    /**
     * List live Channel video from VTV1 - > VTV9
     */
    String LIST_LIVE_CHANNEL = "/http://m.vtv.vn/api/app.ashx?config=livetv/";
    /**
     * Video clip file
     * @param catvideoid is field ZoneVideoId at LIST_VIDEO API
     */
    String VIDEO_CLIP_FILE = "/api/app.ashx?config=video_bycat&catvideoid=/";

    /**------------------------------------------------------------------------------------------ */
    /**
     * List categories Url
     */
    String SOURCE_LINKS = "/api/app.ashx?config=sourceinfo2";

    /**
     * Comment API
     */
    String COMMENT_URL = "/api/app.ashx?config=detail";
    String NEWS_URL = "newsurl=";

    ///api/app.ashx?config=video_newest&channel=vtv1&pageindex=1&pagesize=10
    /**
     * Video related
     */
    String VIDEO_RELATED = "/api/app.ashx?config=video_newest";

    String NUMBER_VIDEO = "pageindex=1&pagesize=300";

    String SEARCH_VIDEO_RELATED = "/api/app.ashx?config=video_search";
}
