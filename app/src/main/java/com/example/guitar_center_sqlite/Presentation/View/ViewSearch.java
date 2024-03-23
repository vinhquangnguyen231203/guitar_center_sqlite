package com.example.guitar_center_sqlite.Presentation.View;

import android.content.Context;
import android.widget.SearchView;

import com.example.guitar_center_sqlite.Domain.Model.Product;

import java.util.List;

public class ViewSearch {
    private Context context;
    private SearchView searchView;
    private List<Product> productList;
    private SearchListener searchListener;
    public ViewSearch(Context context, SearchView searchView) {
        this.context = context;
        this.searchView = searchView;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchListener != null) {
                    searchListener.onQueryTextChange(newText);
                }
                return true;
            }
        });
    }

    public void setSearchListener(SearchListener searchListener)
    {
        this.searchListener = searchListener;
    }
    public interface SearchListener {
        void onQueryTextChange(String newText);
        void onQueryTextSubmit(String query);
    }
}
