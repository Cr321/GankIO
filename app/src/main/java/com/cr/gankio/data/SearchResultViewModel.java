package com.cr.gankio.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.cr.gankio.data.network.SearchResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author RUI CAI
 * @date 2018/8/23
 */
public class SearchResultViewModel extends ViewModel {

    private final String base_url = "https://gank.io/search?q=";
    private OkHttpClient mClient;
    private MutableLiveData<List<SearchResult>> results = new MutableLiveData<>();
    private static final String TAG = "SearchResultViewModel";

    public LiveData<List<SearchResult>> getSearchResult(String key) {
        getSearchResultFromNetwork(key);
        return results;
    }

    private void getSearchResultFromNetwork(String key) {
        final String[] keys = key.split(" ");

        if (mClient == null) {
            mClient = new OkHttpClient();
        }
        Request request = new Request.Builder()
                .url(base_url + key)
                .build();

        mClient.newCall(request).enqueue(new Callback() {

            List<SearchResult> searchResults = new ArrayList<>();

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = response.body().string();
                searchResults.clear();
                Document doc = Jsoup.parse(html);
                Elements content_container = doc.getElementsByClass("container content");
                Element content = content_container.get(0);
                Elements links = content.getElementsByTag("a");
                for (Element link : links) {
                    String linkHref = link.attr("href");
                    String linkText = link.text();
                    searchResults.add(new SearchResult(getSpannableString(linkText),linkHref));
                }
                results.postValue(searchResults);
            }

            private SpannableString getSpannableString(String title) {
                SpannableString result = new SpannableString(title);
                title = title.toLowerCase();
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
                for (String string: keys) {
                    String low_key = string.toLowerCase();
                    int start = title.indexOf(low_key);
                    int last = title.lastIndexOf(low_key);
                    while (start != last) {
                        if (start >= 0) {
                            result.setSpan(colorSpan, start, start + string.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                        start = title.indexOf(low_key, start+low_key.length()-1);
                    }
                    if (start >= 0) {
                        result.setSpan(colorSpan, start, start + string.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
                return result;
            }
        });
    }
}
