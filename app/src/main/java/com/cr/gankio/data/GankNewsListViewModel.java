package com.cr.gankio.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cr.gankio.data.database.GankNews;

import java.util.List;

/**
 * @author RUI CAI
 * @date 2017/11/8
 */

public class GankNewsListViewModel extends ViewModel {
    private final GankRepository mRepository;
    private final String mType;
    private int page;

    public GankNewsListViewModel(GankRepository repository, String type) {
        mRepository = repository;
        mType = type;
        page = 1;
    }

    public LiveData<List<GankNews>> getGankNewsList(String type, int num, int page) {
        return mRepository.getGanksNewsList(type, num, page);
    }

    public void loadMore() {
        mRepository.loadMore(mType, 20, ++page);
    }
}
