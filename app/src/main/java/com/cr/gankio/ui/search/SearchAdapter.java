package com.cr.gankio.ui.search;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.cr.gankio.R;
import com.cr.gankio.data.network.SearchResult;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

/**
 * @author RUI CAI
 * @date 2018/8/23
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<SearchResult> mList;
    private FinestWebView.Builder mFinestWebViewBuilder;

    public void setData(List<SearchResult> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListItemViewHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        String url;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            textView.setText(mList.get(position).getTitle());
            url = mList.get(position).getUrl();
        }

        @Override
        public void onClick(View v) {
            if (mFinestWebViewBuilder == null) {
                mFinestWebViewBuilder = new FinestWebView.Builder(v.getContext().getApplicationContext())
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
    }
}
