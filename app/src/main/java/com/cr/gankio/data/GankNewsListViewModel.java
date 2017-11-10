package com.cr.gankio.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * @author RUI CAI
 * @date 2017/11/8
 */

public class GankNewsListViewModel extends ViewModel {
    private GankRepository mRepository;
    private LiveData<List<GankNews>> mGankNews;

    public GankNewsListViewModel(GankRepository repository) {
        mRepository = repository;
        mGankNews = repository.getGanksNewsList();
    }

    public LiveData<List<GankNews>> getGankNewsList() {
        return mGankNews;
    }
}
