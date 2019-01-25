package com.cr.gankio.ui.gallery;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cr.gankio.GlideApp;
import com.cr.gankio.R;
import com.cr.gankio.data.database.GankNews;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GankNews> items;
    private Activity activity;

    public GalleryAdapter(Activity activity, List<GankNews> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_gallery_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bind(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.welfare_img);
        }

        public void bind(GankNews news) {
            String url = news.getUrl();
            GlideApp.with(activity)
                    .load(url)
                    .placeholder(R.drawable.pic_placehold)
                    .into(imageView);
        }
    }
}
