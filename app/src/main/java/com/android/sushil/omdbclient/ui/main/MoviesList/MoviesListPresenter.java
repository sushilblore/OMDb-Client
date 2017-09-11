package com.android.sushil.omdbclient.ui.main.MoviesList;

import android.util.Log;

import com.android.sushil.omdbclient.data.model.SearchResults;
import com.android.sushil.omdbclient.data.remote.NetworkService;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sushiljha on 09/09/2017.
 */

public class MoviesListPresenter implements MoviesListContract.Presenter {

    private static final String TAG = "MoviesListPresenter";
    private final NetworkService mNetworkService;
    private final MoviesListContract.View mView;
    private CompositeSubscription mSubscriptions;


    public MoviesListPresenter(NetworkService service, MoviesListContract.View view) {
        this.mNetworkService = service;
        this.mView = view;
        this.mSubscriptions = new CompositeSubscription();
    }


    @Override
    public void loadMoviesListItems(String query, String type, int pageNumber) {
        Log.d("Sushil", "MoviesListPresenter  loadMoviesListItems");
        Subscription subscription = mNetworkService.searchMovies(new NetworkService.GetMoviesListCallback() {

            @Override
            public void onSuccess(SearchResults moviesListResponse) {
                Log.i(TAG, "loadMoviesListItems : onSuccess");
                mView.onMovieListLoadSuccess(moviesListResponse);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "loadMoviesListItems : onError : " + e.getLocalizedMessage());
                mView.onMovieListLoadFailure(e);
            }

        }, query, type, pageNumber);

        mSubscriptions.add(subscription);
    }

    public void onStop() {
        mSubscriptions.unsubscribe();
    }
}
