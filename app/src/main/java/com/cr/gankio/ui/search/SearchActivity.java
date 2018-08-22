package com.cr.gankio.ui.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

import com.cr.gankio.R;
import com.cr.gankio.data.SearchResultViewModel;
import com.cr.gankio.data.network.SearchResult;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private SearchResultViewModel mSearchResultViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSearchView = findViewById(R.id.search_view);
        mSearchView.onActionViewExpanded();
        mSearchView.setOnQueryTextListener(this);
        mRecyclerView = findViewById(R.id.search_result);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchResultViewModel = ViewModelProviders.of(this).get(SearchResultViewModel.class);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchResultViewModel.getSearchResult(query).observe(this, new Observer<List<SearchResult>>() {

            @Override
            public void onChanged(@Nullable List<SearchResult> searchResults) {
                if (mAdapter == null) {
                    mAdapter = new SearchAdapter();
                    mRecyclerView.setAdapter(mAdapter);
                }
                mAdapter.setData(searchResults);
                mAdapter.notifyDataSetChanged();
            }
        });
        hideKeyboard();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

        if (mRecyclerView != null) {
            mRecyclerView.requestFocus();
        }
    }
}
