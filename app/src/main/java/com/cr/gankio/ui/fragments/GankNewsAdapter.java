package com.cr.gankio.ui.fragments;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cr.gankio.GlideApp;
import com.cr.gankio.R;
import com.cr.gankio.data.database.GankNews;

import java.util.List;

/**
 * @author RUI CAI
 * @date 2017/11/10
 */

public class GankNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GankNews> items;
    private final GankNewsAdapterOnItemClickHandler mClickHandler;
    private boolean showImage;
    private boolean isLoading = false;
    private GankNewsFragment fragment;

    private final int TYPE_IMAGE = 3000;

    public GankNewsAdapter(GankNewsFragment fragment, List<GankNews> items, GankNewsAdapterOnItemClickHandler mClickHandler) {
        this.items = items;
        this.mClickHandler = mClickHandler;
        this.fragment = fragment;
    }

    public void setshowImage(boolean showImage) {
        this.showImage = showImage;
    }

    public void setItems(List<GankNews> items) {
        this.items = items;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }

    public boolean getLoading() {
        return isLoading;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_IMAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_layout, parent, false);
            return new ImageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_list_layout, parent, false);
            return new ListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            ((ListViewHolder) holder).bind(items.get(position));
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bind(items.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (showImage){
            return TYPE_IMAGE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    interface GankNewsAdapterOnItemClickHandler {
        void onClick(String url);
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView decs;
        private TextView date;

        public ListViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            decs = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        public void bind(GankNews news) {
            title.setText(news.getDesc());
            decs.setText(news.getWho());
            StringBuilder createDate = new StringBuilder(news.getCreatedAt());
            date.setText(createDate.substring(0,createDate.indexOf("T")));
        }

        @Override
        public void onClick(View v) {
            String url = items.get(getAdapterPosition()).getUrl();
            mClickHandler.onClick(url);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private SquareImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.welfare_img);
        }

        public void bind(GankNews news) {
            String url = news.getUrl();
            GlideApp.with(fragment)
                    .load(url)
                    .into(imageView);
        }
    }

}
