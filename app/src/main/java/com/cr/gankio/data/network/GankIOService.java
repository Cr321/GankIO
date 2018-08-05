package com.cr.gankio.data.network;

import com.cr.gankio.data.database.GankNewsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author RUI CAI
 * @date 2017/11/7
 */

public interface GankIOService {

    /**
     * fetch GankIO news by type,num and page
     *
     * @param type type
     * @param num  request number
     * @param page page number
     * @return Call<GankNewsList>
     */
    @GET("data/{type}/{num}/{page}")
    Call<GankNewsList> getGankNewsList(@Path("type") String type, @Path("num") int num, @Path
            ("page") int page);
}
