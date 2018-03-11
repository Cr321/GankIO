package com.cr.gankio.data;

import android.arch.lifecycle.LiveData;

import com.cr.gankio.data.network.GankNewsNetworkDataSource;

import java.util.List;

/**
 * @author RUI CAI
 * @date 2017/11/7
 */
public class GankRepository {
    private static final Object LOCK = new Object();
    private static GankRepository mInstance;
    private final GankNewsNetworkDataSource mGankNewsNetworkDataSource;

    private GankRepository(GankNewsNetworkDataSource gankNewsNetworkDataSource) {
        mGankNewsNetworkDataSource = gankNewsNetworkDataSource;
    }

    public synchronized static GankRepository getInstance(GankNewsNetworkDataSource gankNewsNetworkDataSource) {
        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = new GankRepository(gankNewsNetworkDataSource);
            }
        }
        return mInstance;
    }

    public LiveData<List<GankNews>> getGanksNewsList(String type, int num, int page) {
        return mGankNewsNetworkDataSource.getGanksNewsList(type, num, page);
    }

    public LiveData<List<GankNews>> loadMore(String type, int num, int page) {
        return mGankNewsNetworkDataSource.loadMore(type, num, page);
    }
}
