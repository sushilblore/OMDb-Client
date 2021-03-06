package com.android.sushil.omdbclient.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;
    int visibleThreshold = 1;


    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

        if (!isLoading() && (totalItemCount > 0) && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            loadMoreItems();
        }

    }

    protected abstract void loadMoreItems();

    public abstract boolean isLoading();

}
