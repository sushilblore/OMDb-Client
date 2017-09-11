package com.android.sushil.omdbclient.data.remote;

import com.android.sushil.omdbclient.data.model.MovieDetails;
import com.android.sushil.omdbclient.data.model.SearchResults;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sushiljha on 09/09/2017.
 */

public class NetworkService {
    private final NetworkAPI networkService;

    public NetworkService(NetworkAPI networkService) {
        this.networkService = networkService;
    }


    public Subscription searchMovies(final GetMoviesListCallback callback,
                                     String searchTitle, String type, int pageNumber) {

        return networkService.searchMovies(searchTitle, type, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends SearchResults>>() {
                    @Override
                    public Observable<? extends SearchResults> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<SearchResults>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);

                    }

                    @Override
                    public void onNext(SearchResults moviesListResponse) {
                        callback.onSuccess(moviesListResponse);

                    }
                });
    }

    public Subscription getMovieDetails(final GetMovieDetailsCallback callback,
                                     String id) {

        return networkService.getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MovieDetails>>() {
                    @Override
                    public Observable<? extends MovieDetails> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MovieDetails>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);

                    }

                    @Override
                    public void onNext(MovieDetails movieDetailsResponse) {
                        callback.onSuccess(movieDetailsResponse);

                    }
                });
    }

    public interface GetMoviesListCallback{
        void onSuccess(SearchResults moviesListResponse);
        void onError(Throwable e);
    }

    public interface GetMovieDetailsCallback{
        void onSuccess(MovieDetails movieDetailsResponse);
        void onError(Throwable e);
    }
}
