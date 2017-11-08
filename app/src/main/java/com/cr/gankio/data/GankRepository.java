package com.cr.gankio.data;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author RUI CAI
 * @date 2017/11/7
 */

public class GankRepository {
    private static final Object LOCK = new Object();
    private static GankRepository sInstance;
    private static final String baseurl = "http://gank.io/api/";

    public static Call<GankNewsList> getGanksNewsList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GankIOService gankIOService = retrofit.create(GankIOService.class);
        Call<GankNewsList> call = gankIOService.getGankNewsList("all", 20, 1);
        return call;
    }
}
