package com.cr.gankio.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cr.gankio.Constants;
import com.cr.gankio.R;
import com.cr.gankio.data.GankNewsListViewModel;
import com.cr.gankio.data.GankRepository;
import com.cr.library.ui.ExtendRecyclerView;
import com.thefinestartist.finestwebview.FinestWebView;

/**
 * @author RUI CAI
 * @date 2017/11/10
 */

public class GankNewsFragment extends Fragment implements GankNewsAdapter
        .GankNewsAdapterOnItemClickHandler {
    private ExtendRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_load;
    private ProgressBar progressBar;
    private GankNewsListViewModel mViewModel;
    private GankNewsAdapter mAdapter;
    private View rootView;
    private GankNewsFragment mInstance;
    private FinestWebView.Builder mFinestWebViewBuilder;

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
        mInstance = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news_list_layout, container, false);
            initView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        GankNewsListViewModelFactory factory = new GankNewsListViewModelFactory(GankRepository
                .getInstance(getActivity()), mType);
        mViewModel = ViewModelProviders.of(this, factory).get(GankNewsListViewModel.class);
        if (!Constants.TYPE_WELFARE.equals(mType)) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL);
            manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    manager.invalidateSpanAssignments();
                }
            });
            mRecyclerView.setLayoutManager(manager);
        }
        View foot_view = LayoutInflater.from(getContext()).inflate(R.layout.footer_layout,
                mRecyclerView, false);
        tv_load = foot_view.findViewById(R.id.footer_tv);
        progressBar = foot_view.findViewById(R.id.footer_progressbar);
        foot_view.setOnClickListener(v -> {
            if (!mAdapter.getLoading()) {
                tv_load.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                mViewModel.loadMore();
                mAdapter.setLoading(true);
            }
        });
        mRecyclerView.addFooterView(foot_view);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);


        mViewModel.getGankNewsList(mType, 20, 1).observe(this, mGankNews -> {
                    if (mAdapter == null) {
                        mAdapter = new GankNewsAdapter(mInstance, mGankNews, mInstance);
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
                }
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(String url) {
        if (mFinestWebViewBuilder == null) {
            mFinestWebViewBuilder = new FinestWebView.Builder(getActivity().getApplicationContext())
                    .toolbarColorRes(R.color.colorPrimary)
                    .statusBarColorRes(R.color.colorPrimaryDark)
                    .titleColorRes(android.R.color.white)
                    .iconDefaultColorRes(android.R.color.white)
                    .webViewJavaScriptEnabled(true)
                    .webViewUseWideViewPort(true)
                    .webViewLoadWithOverviewMode(true)
                    .webViewSupportZoom(true)
                    .webViewBuiltInZoomControls(true)
                    .webViewDisplayZoomControls(false)
                    .webViewMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
                    .webViewLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        mFinestWebViewBuilder.show(url);
    }

    private void refresh() {
        //Todo Will modify lately
        mViewModel.getGankNewsList(mType, 20, 1);
    }
}
