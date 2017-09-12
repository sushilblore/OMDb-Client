package com.android.sushil.omdbclient.ui.main.MovieDetails;

import android.util.Log;

import com.android.sushil.omdbclient.data.model.MovieDetails;
import com.android.sushil.omdbclient.data.remote.NetworkService;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sushiljha on 10/09/2017.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private static final String TAG = "MoviesListPresenter";
    private final NetworkService mNetworkService;
    private final MovieDetailsContract.View mView;
    private CompositeSubscription mSubscriptions;

    public MovieDetailsPresenter(NetworkService service, MovieDetailsContract.View view) {
        this.mNetworkService = service;
        this.mView = view;
        this.mSubscriptions = new CompositeSubscription();
    }


    @Override
    public void loadMovieDetails(String id) {
        Subscription subscription = mNetworkService.getMovieDetails(new NetworkService.GetMovieDetailsCallback() {

            @Override
            public void onSuccess(MovieDetails movieDetailsResponse) {
                mView.onMovieDetailsLoadSuccess(movieDetailsResponse);
            }

            @Override
            public void onError(Throwable e) {
                mView.onMovieDetailsLoadFailure(e);
            }

        }, id);

        if(null != subscription)
            mSubscriptions.add(subscription);
    }

    public void onStop() {
        mSubscriptions.unsubscribe();
    }
}
