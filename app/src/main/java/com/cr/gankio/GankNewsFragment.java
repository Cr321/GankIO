package com.cr.gankio;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cr.gankio.data.GankNewsListViewModel;
import com.cr.gankio.data.GankRepository;

import javax.inject.Inject;

/**
 * @author RUI CAI
 * @date 2017/11/10
 */

public class GankNewsFragment extends Fragment implements GankNewsAdapter.GankNewsAdapterOnItemClickHandler{
    private RecyclerView mRecyclerView;
    private GankNewsListViewModel mViewModel;

    public static GankNewsFragment newInstance() {
        return new GankNewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getActivity().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GankNewsListViewModelFactory factory = new GankNewsListViewModelFactory(new GankRepository());
        mViewModel = ViewModelProviders.of(this,factory).get(GankNewsListViewModel.class);
        mViewModel.getGankNewsList().observe(this, mGankNews-> {
            GankNewsAdapter mAdapter = new GankNewsAdapter(mGankNews,this::onClick);
            mRecyclerView.setAdapter(mAdapter);
        });
    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(this.getActivity(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
