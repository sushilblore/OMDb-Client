package com.android.sushil.omdbclient.ui.main.MoviesList;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sushil.omdbclient.BaseApplication;
import com.android.sushil.omdbclient.R;
import com.android.sushil.omdbclient.data.model.Search;
import com.android.sushil.omdbclient.data.model.SearchResults;
import com.android.sushil.omdbclient.data.remote.NetworkService;
import com.android.sushil.omdbclient.utils.ActivityUtils;
import com.android.sushil.omdbclient.utils.PaginationAdapterCallback;
import com.android.sushil.omdbclient.utils.PaginationScrollListener;

import javax.inject.Inject;

public class MoviesListActivity extends AppCompatActivity implements MoviesListContract.View,
        PaginationAdapterCallback {

    private static final String TAG = "MoviesListFragment";
    private static final String TYPE = "movie";
    private Toolbar mToolbar;
    private MoviesListPresenter mPresenter;
    private MoviesListAdapter mAdapter;
    ProgressBar mProgressBar;
    private TextView mMessage;
    LinearLayoutManager mLayoutManager;

    RecyclerView mRecyclerView;
    private SearchView mSearchView;

    private static final int PAGE_START = 1;
    private boolean mIsLoading = false;
    private boolean mReachedEnd = false;
    private int mCurrentPage = PAGE_START;

    private String mLatestQuery;
    private boolean mIsNewQuery = true;


    @Inject
    public NetworkService mNetworkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ((BaseApplication)getApplication()).getDependency().inject(this);
        setPresenter(new MoviesListPresenter(mNetworkService, this));

        mProgressBar = (ProgressBar) findViewById(R.id.main_progress);
        mMessage = (TextView) findViewById(R.id.message);
        mAdapter = new MoviesListAdapter(this, new MoviesListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Search item) {

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                fetchData(mLatestQuery, false);
                mIsLoading = true;
            }

            @Override
            public boolean isLastPage() {
                return mReachedEnd;
            }

            @Override
            public boolean isLoading() {
                return mIsLoading;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_search, menu);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Sushil", "..onQueryTextSubmit....");
                mLatestQuery = query.toString().trim();
                ActivityUtils.hideSoftKeyboard(MoviesListActivity.this);
                mAdapter.removeAll();

                fetchData(mLatestQuery, true);
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Sushil", "..onQueryTextChange....");
                return false;
            }
        };
        mSearchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setPresenter(MoviesListContract.Presenter presenter) {
        mPresenter = (MoviesListPresenter)presenter;
    }

    private void fetchData(final String query, boolean newQuery) {
        Log.d("Sushil", "...fetchData    newQuery : " + newQuery);
        mProgressBar.setVisibility(View.VISIBLE);
        if(newQuery) {
            mCurrentPage = PAGE_START;
            mReachedEnd= false;
            mPresenter.loadMoviesListItems(query, TYPE, mCurrentPage);
        }
        else {
            if (!mReachedEnd) {
                mCurrentPage++;
                mPresenter.loadMoviesListItems(query, TYPE, mCurrentPage);
            }
        }
        mIsNewQuery = newQuery;
    }

    @Override
    public void onMovieListLoadSuccess(SearchResults searchResultResponse) {
        Log.d("Sushil", "...onMovieListLoadSuccess mIsNewQuery : " + mIsNewQuery + "response : " + searchResultResponse.getResponse().toString());
        mProgressBar.setVisibility(View.GONE);
        if(mIsNewQuery) {
            if (searchResultResponse.getResponse().equals("True")) {
                mMessage.setVisibility(View.GONE);
                mAdapter.addAll(searchResultResponse.getSearch());
            } else {
                mMessage.setText(R.string.error_no_movies_found);
                mMessage.setVisibility(View.VISIBLE);
            }
            mIsLoading = false;
        }
        else {
            if (!mReachedEnd) {
                if (searchResultResponse.getResponse().equals("True")) {
                    mAdapter.addAll(searchResultResponse.getSearch());
                } else {
                    mReachedEnd = true;
                }
            }
            mIsLoading = false;
        }
    }

    @Override
    public void onMovieListLoadFailure(Throwable t) {
        mProgressBar.setVisibility(View.GONE);
        if(mIsNewQuery) {
        }
        else {
            if (!mReachedEnd) {
                mIsLoading = false;
            }
        }
        String errorText = ActivityUtils.fetchErrorMessage(t, this);
        //mMessage.setText(errorText);
        //mMessage.setVisibility(View.VISIBLE);
        Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void retryPageLoad() {
        fetchData(mLatestQuery, false);
    }
}
