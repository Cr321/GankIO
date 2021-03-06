package com.cr.gankio.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
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
    private Map<String, MutableLiveData<List<GankNews>>> GankNewsMaps;

    private GankRepository(Context context) {
        mDatabase = Room.databaseBuilder(context.getApplicationContext(),
                GankIODatabase.class, "gankio").build();
        gankNewsDao = mDatabase.gankNewsDao();
        GankNewsMaps = new HashMap<>();
        mGankNewsNetworkDataSource = GankNewsNetworkDataSource.getInstance(GankNewsMaps);
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
        if (!GankNewsMaps.containsKey(type)) {
            temp = new MutableLiveData<>();
            Executors.newSingleThreadExecutor().execute(() -> temp.postValue(gankNewsDao
                    .getByType(type)));
            temp.observeForever(gankNews -> Executors.newSingleThreadExecutor().execute(() ->
                    gankNewsDao.insertAll(gankNews)));
            GankNewsMaps.put(type, temp);
        } else {
            temp = GankNewsMaps.get(type);
        }
        mGankNewsNetworkDataSource.getGanksNewsList(type, num, page);
        return temp;
    }

    public void loadMore(String type, int num, int page) {
        mGankNewsNetworkDataSource.loadMore(type, num, page);
    }
}
