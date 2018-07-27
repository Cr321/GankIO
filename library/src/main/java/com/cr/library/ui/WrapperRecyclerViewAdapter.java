package com.cr.library.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author RUI CAI
 * @date 2018/03/10
 */

public class WrapperRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ExtendRecyclerView.FixedViewInfo> mHeaderViewInfos;
    private ArrayList<ExtendRecyclerView.FixedViewInfo> mFooterViewInfos;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<ExtendRecyclerView.FixedViewInfo> EMPTY_INFO_LIST = new ArrayList<>();
    private boolean isStaggered;

    public WrapperRecyclerViewAdapter(ArrayList<ExtendRecyclerView.FixedViewInfo> headerViewInfos,
                                      ArrayList<ExtendRecyclerView.FixedViewInfo> footerViewInfos,
                                      RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        if (headerViewInfos == null) {
            mHeaderViewInfos = EMPTY_INFO_LIST;
        } else {
            mHeaderViewInfos = headerViewInfos;
        }
        if (footerViewInfos == null) {
            mFooterViewInfos = EMPTY_INFO_LIST;
        } else {
            mFooterViewInfos = footerViewInfos;
        }
    }

    public int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    public int getFootersCount() {
        return mFooterViewInfos.size();
    }

    public void adjustSpanSize(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int numHeaders = getHeadersCount();
                    int adjPosition = position - numHeaders;
                    if (position < numHeaders || adjPosition >= mAdapter.getItemCount())
                        return manager.getSpanCount();
                    return 1;
                }
            });
        }

        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            isStaggered = true;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType >= ExtendRecyclerView.BASE_HEADER_VIEW_TYPE && viewType < ExtendRecyclerView.BASE_HEADER_VIEW_TYPE + getHeadersCount()) {
            View view = mHeaderViewInfos.get(viewType - ExtendRecyclerView.BASE_HEADER_VIEW_TYPE).view;
            return viewHolder(view);
        } else if (viewType >= ExtendRecyclerView.BASE_FOOTER_VIEW_TYPE && viewType < ExtendRecyclerView.BASE_FOOTER_VIEW_TYPE + getFootersCount()) {
            View view = mFooterViewInfos.get(viewType - ExtendRecyclerView.BASE_FOOTER_VIEW_TYPE).view;
            return viewHolder(view);
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return;
        }
        int adjPosition = position - numHeaders;
        int adapterCount;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
        } else {
            return getHeadersCount() + getFootersCount();
        }
    }

    @Override
    public long getItemId(int position) {
        int numHeaders = getHeadersCount();
        if (mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemId(adjPosition);
            }
        }
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return mHeaderViewInfos.get(position).viewType;
        }
        int adjPosition = position - numHeaders;
        int adapterPosition = 0;
        if (mAdapter != null) {
            adapterPosition = mAdapter.getItemCount();
            if (adjPosition < adapterPosition) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        return mFooterViewInfos.get(position - adapterPosition - getHeadersCount()).viewType;
    }

    private RecyclerView.ViewHolder viewHolder(final View itemView) {

        if (isStaggered) {
            StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT,
                    StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
            params.setFullSpan(true);
            itemView.setLayoutParams(params);
        }
        return new RecyclerView.ViewHolder(itemView) {
        };
    }

}
