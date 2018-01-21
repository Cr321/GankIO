package com.cr.gankio.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author RUI CAI
 * @date 2017/11/7
 */

public interface GankIOService {
    String TYPE_ALL = "all";
    String TYPE_ANDROID = "Android";
    String TYPE_IOS = "iOS";
    String TYPE_VIDEO = "休息视频";
    String TYPE_WELFARE = "福利";
    String TYPE_EXPAND = "拓展资源";
    String TYPE_FRONT_END = "前端";
    String TYPE_XIA = "瞎推荐";
    String TYPE_APP = "App";

    /**
     * fetch GankIO news by type,num and page
     *
     * @param type type
     * @param num request number
     * @param page page number
     * @return Call<GankNewsList>
     */
    @GET("data/{type}/{num}/{page}")
    Call<GankNewsList> getGankNewsList(@Path("type") String type, @Path("num") int num, @Path("page") int page);
}
