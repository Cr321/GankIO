package com.cr.gankio.data.network;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.cr.gankio.data.database.GankNews;
import com.cr.gankio.data.database.GankNewsList;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author RUI CAI
 * @date 2018/1/21
 */

public class GankNewsNetworkDataSource {

    private static final String TAG = "NetworkDataSource";

    private static final Object LOCK = new Object();
    private static final String baseurl = "http://gank.io/api/";
    private static volatile GankNewsNetworkDataSource mInstance;
    private final Map<String, MutableLiveData<List<GankNews>>> datas;
    private final GankIOService gankIOService;

    private GankNewsNetworkDataSource(Map<String, MutableLiveData<List<GankNews>>> datas) {
        this.datas = datas;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gankIOService = retrofit.create(GankIOService.class);
    }

    public synchronized static GankNewsNetworkDataSource getInstance(Map<String,
            MutableLiveData<List<GankNews>>> datas) {
        if (mInstance == null) {
            synchronized (LOCK) {
                if (mInstance == null) {
                    mInstance = new GankNewsNetworkDataSource(datas);
                }
            }
        }
        return mInstance;
    }

    public void getGanksNewsList(final String type, int num, int page) {
        Call<GankNewsList> call = gankIOService.getGankNewsList(type, num, page);
        call.enqueue(new Callback<GankNewsList>() {
            @Override
            public void onResponse(Call<GankNewsList> call, Response<GankNewsList> response) {
                List<GankNews> result = response.body().getResults();
                MutableLiveData<List<GankNews>> data = datas.get(type);
                int result_size = result.size();
                if (result_size > 0) {
                    int index = 0;
                    List<GankNews> temp = data.getValue();
                    if (temp.size() > 0) {
                        String target_id = temp.get(0).get_id();
                        while (index < result_size && !result.get(index).get_id().equals
                                (target_id)) {
                            index++;
                        }
                        if (index != result_size) {
                            result = result.subList(0, index);
                        }
                    }
                }
                result.addAll(data.getValue());
                data.setValue(result);
            }

            @Override
            public void onFailure(Call<GankNewsList> call, Throwable t) {
                Log.d(TAG, "onFailure! ");
            }
        });
    }

    public void loadMore(final String type, int num, int page) {
        Call<GankNewsList> call = gankIOService.getGankNewsList(type, num, page);
        call.enqueue(new Callback<GankNewsList>() {
            @Override
            public void onResponse(Call<GankNewsList> call, Response<GankNewsList> response) {
                List<GankNews> temp = datas.get(type).getValue();
                temp.addAll(response.body().getResults());
                datas.get(type).setValue(temp);
            }

            @Override
            public void onFailure(Call<GankNewsList> call, Throwable t) {
                Log.d(TAG, "onFailure! ");
            }
        });
    }
}
