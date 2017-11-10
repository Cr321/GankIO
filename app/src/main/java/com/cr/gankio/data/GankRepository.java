package com.cr.gankio.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author RUI CAI
 * @date 2017/11/7
 */
public class GankRepository {
    private static final String baseurl = "http://gank.io/api/";

    public LiveData<List<GankNews>> getGanksNewsList() {
        final MutableLiveData<List<GankNews>> data = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GankIOService gankIOService = retrofit.create(GankIOService.class);
        Call<GankNewsList> call = gankIOService.getGankNewsList("all", 20, 1);
        call.enqueue(new Callback<GankNewsList>() {
            @Override
            public void onResponse(Call<GankNewsList> call, Response<GankNewsList> response) {
                data.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<GankNewsList> call, Throwable t) {

            }
        });
        return data;
    }
}
