package com.cr.gankio.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cr.gankio.R;
import com.cr.gankio.data.GankNews;

import java.util.List;

/**
 * @author RUI CAI
 * @date 2017/11/10
 */

public class GankNewsAdapter extends RecyclerView.Adapter<GankNewsAdapter.ViewHolder> {
    private List<GankNews> items;
    private final GankNewsAdapterOnItemClickHandler mClickHandler;
    private boolean showImage;
    private GankNewsFragment fragment;

    public GankNewsAdapter(GankNewsFragment fragment, List<GankNews> items, GankNewsAdapterOnItemClickHandler mClickHandler) {
        this.items = items;
        this.mClickHandler = mClickHandler;
        this.fragment = fragment;
    }

    public void setshowImage(boolean showImage) {
        this.showImage = showImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (showImage) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_layout, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_list_layout, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        if (null == items) {
            return 0;
        } else {
            return items.size();
        }
    }

    interface GankNewsAdapterOnItemClickHandler {
        void onClick(String url);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView decs;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            if (showImage) {
                imageView = itemView.findViewById(R.id.welfare_img);
            } else {
                title = itemView.findViewById(R.id.title);
                decs = itemView.findViewById(R.id.desc);
                itemView.setOnClickListener(this);
            }
        }

        public void bind(GankNews news) {
            if (showImage) {
                String url = items.get(getAdapterPosition()).getUrl();
                Glide.with(fragment).load(url).into(imageView);
            } else {
                title.setText(news.getDesc());
                decs.setText(news.getType());
            }

        }

        @Override
        public void onClick(View v) {
            String url = items.get(getAdapterPosition()).getUrl();
            mClickHandler.onClick(url);
        }
    }

}
