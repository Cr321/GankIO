package com.cr.gankio.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cr.gankio.R;
import com.cr.gankio.data.GankNews;

import java.util.List;

/**
 * @author RUI CAI
 * @date 2017/11/10
 */

public class GankNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GankNews> items;
    private final GankNewsAdapterOnItemClickHandler mClickHandler;
    private boolean showImage;
    private GankNewsFragment fragment;

    private final int TYPE_FOOTER = 2000;
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_IMAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_layout, parent, false);
            return new ImageViewHolder(view);
        } else if (viewType == TYPE_FOOTER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
            return new FooterViewHolder(view);
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
        } else if (holder instanceof FooterViewHolder) {
            //TODO;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1) {
            return TYPE_FOOTER;
        } else if (showImage){
            return TYPE_IMAGE;
        } else {
            return super.getItemViewType(position);
        }
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
        void loadMore();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView decs;

        public ListViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            decs = itemView.findViewById(R.id.desc);
            itemView.setOnClickListener(this);
        }

        public void bind(GankNews news) {
            title.setText(news.getDesc());
            decs.setText(news.getType());
        }

        @Override
        public void onClick(View v) {
            String url = items.get(getAdapterPosition()).getUrl();
            mClickHandler.onClick(url);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.welfare_img);
        }

        public void bind(GankNews news) {
            String url = items.get(getAdapterPosition()).getUrl();
            Glide.with(fragment).load(url).into(imageView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar progressBar;
        private TextView textView;

        public FooterViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.footer_tv);
            progressBar = itemView.findViewById(R.id.footer_progressbar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.loadMore();
            textView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}
