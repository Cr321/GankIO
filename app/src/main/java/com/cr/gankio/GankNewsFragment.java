package com.cr.gankio;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cr.gankio.data.GankIOService;
import com.cr.gankio.data.GankNewsListViewModel;
import com.cr.gankio.data.GankRepository;
import com.cr.gankio.data.network.GankNewsNetworkDataSource;

import javax.inject.Inject;

/**
 * @author RUI CAI
 * @date 2017/11/10
 */

public class GankNewsFragment extends Fragment implements GankNewsAdapter.GankNewsAdapterOnItemClickHandler{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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
        mSwipeRefreshLayout = getActivity().findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        GankNewsNetworkDataSource gankNewsNetworkDataSource = GankNewsNetworkDataSource.getInstance();
        GankNewsListViewModelFactory factory = new GankNewsListViewModelFactory(GankRepository.getInstance(gankNewsNetworkDataSource));
        mViewModel = ViewModelProviders.of(this,factory).get(GankNewsListViewModel.class);
        mViewModel.getGankNewsList(GankIOService.TYPE_ALL, 20, 1).observe(this, mGankNews-> {
            GankNewsAdapter mAdapter = new GankNewsAdapter(mGankNews,this::onClick);
            mRecyclerView.setAdapter(mAdapter);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(this.getActivity(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void refresh() {
        //Todo Will modify lately
        mViewModel.getGankNewsList(GankIOService.TYPE_APP, 20, 1);
    }
}
