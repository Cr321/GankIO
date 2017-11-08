package com.cr.gankio.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * @author RUI CAI
 * @date 2017/11/8
 */

public class GankNewsListViewModel extends ViewModel {
    private final GankRepository mRepository;
    private final LiveData<List<GankNews>> mGankNews;

    public GankNewsListViewModel(GankRepository repository) {
        mRepository = repository;
        mGankNews = null;
    }

    public LiveData<List<GankNews>> getGankNewsList() {
        return mGankNews;
    }
}
