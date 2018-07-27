package com.cr.gankio.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.cr.gankio.data.database.GankIODatabase;
import com.cr.gankio.data.database.GankNews;
import com.cr.gankio.data.database.GankNewsDao;
import com.cr.gankio.data.network.GankNewsNetworkDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * @author RUI CAI
 * @date 2017/11/7
 */
public class GankRepository {
    private static final Object LOCK = new Object();
    private static volatile GankRepository mInstance;
    private final GankNewsNetworkDataSource mGankNewsNetworkDataSource;
    private final GankIODatabase mDatabase;
    private GankNewsDao gankNewsDao;
    private Map<String, MutableLiveData<List<GankNews>>> maps;

    private GankRepository(Context context) {
        mDatabase = Room.databaseBuilder(context.getApplicationContext(),
                GankIODatabase.class, "gankio").build();
        gankNewsDao = mDatabase.gankNewsDao();
        maps = new HashMap<>();
        mGankNewsNetworkDataSource = GankNewsNetworkDataSource.getInstance(maps);
    }

    public synchronized static GankRepository getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                if (mInstance == null) {
                    mInstance = new GankRepository(context);
                }
            }
        }
        return mInstance;
    }

    public LiveData<List<GankNews>> getGanksNewsList(final String type, int num, int page) {
        final MutableLiveData<List<GankNews>> temp;
        if (!maps.containsKey(type)) {
            temp = new MutableLiveData<>();
            Executors.newSingleThreadExecutor().execute(()-> temp.postValue(gankNewsDao.getByType(type)));
            temp.observeForever(gankNews-> Executors.newSingleThreadExecutor().execute(()-> gankNewsDao.insertAll(gankNews)));
            maps.put(type, temp);
        } else {
            temp = maps.get(type);
        }
        mGankNewsNetworkDataSource.getGanksNewsList(type, num, page);
        return temp;
    }

    public void loadMore(String type, int num, int page) {
        mGankNewsNetworkDataSource.loadMore(type, num, page);
    }
}
