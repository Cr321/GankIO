package com.cr.library.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * @author RUI CAI
 * @date 2018/03/10
 */

public class ExtendRecyclerView extends RecyclerView {

    public class FixedViewInfo {
        public View view;
        public int viewType;
    }

    ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();
    ArrayList<FixedViewInfo> mFooterViewInfos = new ArrayList<>();
    public Adapter mAdapter;
    private boolean isShouldSpan;
    public static final int BASE_HEADER_VIEW_TYPE = -1 << 10;
    public static final int BASE_FOOTER_VIEW_TYPE = -1 << 11;

    public ExtendRecyclerView(Context context) {
        super(context);
    }

    public ExtendRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeaderView(View view) {

        FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.viewType = BASE_HEADER_VIEW_TYPE + mHeaderViewInfos.size();
        mHeaderViewInfos.add(info);

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void addFooterView(View view) {

        FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.viewType = BASE_FOOTER_VIEW_TYPE + mFooterViewInfos.size();
        mFooterViewInfos.add(info);

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof WrapperRecyclerViewAdapter)) {
            mAdapter = new WrapperRecyclerViewAdapter(mHeaderViewInfos, mFooterViewInfos, adapter);
        }
        super.setAdapter(mAdapter);

        if (isShouldSpan) {
            ((WrapperRecyclerViewAdapter) mAdapter).adjustSpanSize(this);
        }
    }

    @Override
    public Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof GridLayoutManager || layout instanceof StaggeredGridLayoutManager) {
            isShouldSpan = true;
        }
        super.setLayoutManager(layout);
    }
}
