package com.cr.gankio;

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

    public GankNewsListViewModelFactory(GankRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GankNewsListViewModel(mRepository);
    }
}
