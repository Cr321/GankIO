package com.cr.gankio.ui.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.cr.gankio.data.GankNewsListViewModel;
import com.cr.gankio.data.GankRepository;

/**
 * @author RUI CAI
 * @date 2017/11/10
 */

public class GankNewsListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final GankRepository mRepository;
    private final String mType;

    public GankNewsListViewModelFactory(GankRepository repository, String type) {
        this.mRepository = repository;
        this.mType = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GankNewsListViewModel(mRepository, mType);
    }
}
