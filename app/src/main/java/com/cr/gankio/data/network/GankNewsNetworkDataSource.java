package com.cr.gankio.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.cr.gankio.data.GankIOService;
import com.cr.gankio.data.GankNews;
import com.cr.gankio.data.GankNewsList;

import java.util.HashMap;
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

	private static final String TAG = "GankNewsNetworkDataSource";

	private static final Object LOCK = new Object();
	private static GankNewsNetworkDataSource mInstance;
	private final Map<String, MutableLiveData<List<GankNews>>> datas;
	private final GankIOService gankIOService;

	private static final String baseurl = "http://gank.io/api/";

	private GankNewsNetworkDataSource() {
		datas = new HashMap<>();
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(baseurl)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		gankIOService = retrofit.create(GankIOService.class);
	}

	public synchronized static GankNewsNetworkDataSource getInstance() {
		if (mInstance == null) {
			synchronized (LOCK) {
				mInstance = new GankNewsNetworkDataSource();
			}
		}
		return mInstance;
	}

	public LiveData<List<GankNews>> getGanksNewsList(String type, int num, int page) {
		Call<GankNewsList> call = gankIOService.getGankNewsList(type, num, page);
		if (!datas.containsKey(type)) {
			MutableLiveData<List<GankNews>> data = new MutableLiveData<>();
			datas.put(type, data);
		}
		call.enqueue(new Callback<GankNewsList>() {
			@Override
			public void onResponse(Call<GankNewsList> call, Response<GankNewsList> response) {
				datas.get(type).setValue(response.body().getResults());
			}

			@Override
			public void onFailure(Call<GankNewsList> call, Throwable t) {
				Log.d(TAG, "onFailure: ");
			}
		});
		return datas.get(type);
	}

}
