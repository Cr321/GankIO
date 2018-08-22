package com.cr.gankio.data.network;

import android.text.SpannableString;

/**
 * @author RUI CAI
 * @date 2018/8/23
 */
public class SearchResult {
    private SpannableString title;
    private String url;

    public SearchResult(SpannableString title, String url) {
        this.title = title;
        this.url = url;
    }

    public SpannableString getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
