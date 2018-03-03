package com.cr.gankio.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cr.gankio.Constants;
import com.cr.gankio.R;
import com.cr.gankio.data.GankIOService;
import com.cr.gankio.data.GankNewsListViewModel;
import com.cr.gankio.data.GankRepository;
import com.cr.gankio.data.network.GankNewsNetworkDataSource;
import com.cr.gankio.ui.web.WebActivity;

/**
 * @author RUI CAI
 * @date 2017/11/10
 */

public class GankNewsFragment extends Fragment implements GankNewsAdapter.GankNewsAdapterOnItemClickHandler{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GankNewsListViewModel mViewModel;
    private View rootView;

    private static final String ARG_TYPE = "type";
    private String mType;

    public static GankNewsFragment newInstance(String type) {
        GankNewsFragment fragment = new GankNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news_list_layout, container, false);
            initView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        if (!Constants.TYPE_WELFARE.equals(mType)) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        GankNewsNetworkDataSource gankNewsNetworkDataSource = GankNewsNetworkDataSource.getInstance();
        GankNewsListViewModelFactory factory = new GankNewsListViewModelFactory(GankRepository.getInstance(gankNewsNetworkDataSource), mType);
        mViewModel = ViewModelProviders.of(this,factory).get(GankNewsListViewModel.class);
        mViewModel.getGankNewsList(mType, 20, 1).observe(this, mGankNews-> {
            GankNewsAdapter mAdapter = new GankNewsAdapter(this, mGankNews,this::onClick);
            if (Constants.TYPE_WELFARE.equals(mType)) {
                mAdapter.setshowImage(true);
            }
            mRecyclerView.setAdapter(mAdapter);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(this.getActivity(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void refresh() {
        //Todo Will modify lately
        mViewModel.getGankNewsList(mType, 20, 1);
    }
}
