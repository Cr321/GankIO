package com.cr.gankio.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cr.gankio.Constants;
import com.cr.gankio.R;
import com.cr.gankio.data.GankNewsListViewModel;
import com.cr.gankio.data.GankRepository;
import com.cr.gankio.data.network.GankNewsNetworkDataSource;
import com.cr.gankio.ui.web.WebActivity;
import com.cr.library.ui.ExtendRecyclerView;

/**
 * @author RUI CAI
 * @date 2017/11/10
 */

public class GankNewsFragment extends Fragment implements GankNewsAdapter.GankNewsAdapterOnItemClickHandler{
    private ExtendRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_load;
    private ProgressBar progressBar;
    private GankNewsListViewModel mViewModel;
    private GankNewsAdapter mAdapter;
    private View rootView;

    private static final String ARG_TYPE = "type";
    private String mType;

    private static final String TAG = "GankNewsFragment";

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
        GankNewsNetworkDataSource gankNewsNetworkDataSource = GankNewsNetworkDataSource.getInstance();
        GankNewsListViewModelFactory factory = new GankNewsListViewModelFactory(GankRepository.getInstance(gankNewsNetworkDataSource), mType);
        mViewModel = ViewModelProviders.of(this,factory).get(GankNewsListViewModel.class);
        if (!Constants.TYPE_WELFARE.equals(mType)) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        View foot_view = LayoutInflater.from(getContext()).inflate(R.layout.footer_layout, mRecyclerView, false);
        tv_load = foot_view.findViewById(R.id.footer_tv);
        progressBar = foot_view.findViewById(R.id.footer_progressbar);
        foot_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAdapter.getLoading()) {
                    tv_load.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    mViewModel.loadMore();
                    mAdapter.setLoading(true);
                }
            }
        });
        mRecyclerView.addFooterView(foot_view);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


        mViewModel.getGankNewsList(mType, 20, 1).observe(this, mGankNews-> {
            if (mAdapter == null) {
                mAdapter = new GankNewsAdapter(this, mGankNews,this);
                if (Constants.TYPE_WELFARE.equals(mType)) {
                    mAdapter.setshowImage(true);
                }
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setItems(mGankNews);
                mAdapter.setLoading(false);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                tv_load.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
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
