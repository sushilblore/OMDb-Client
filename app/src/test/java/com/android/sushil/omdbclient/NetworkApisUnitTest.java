package com.android.sushil.omdbclient;

import com.android.sushil.omdbclient.data.model.MovieDetails;
import com.android.sushil.omdbclient.data.model.SearchResults;
import com.android.sushil.omdbclient.data.remote.NetworkAPI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import retrofit2.http.Query;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by sushiljha on 11/09/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class NetworkApisUnitTest {

    NetworkAPI mNetworkAPI;

    @Before
    public void setUp() {
        mNetworkAPI = new NetworkAPI() {
            @Override
            public Observable<SearchResults> searchMovies(@Query("s") String query, @Query("type") String type, @Query("page") int page) {
                SearchResults searchResults = new SearchResults();
                return Observable.just(searchResults);
            }

            @Override
            public Observable<MovieDetails> getMovieDetails(@Query("i") String imdbId) {
                MovieDetails movieDetails = new MovieDetails();
                return Observable.just(movieDetails);
            }
        };
    }

    @Test
    public void testSearchMoviesApi() throws Exception {
        TestSubscriber<SearchResults> subscriber = TestSubscriber.create();
        if(null != mNetworkAPI) {
            mNetworkAPI.searchMovies("Live", "movie", 1).subscribe(subscriber);
            subscriber.assertNoErrors();
            subscriber.assertCompleted();

        }
    }

    @Test
    public void testMovieDetailsApi() throws Exception {
        TestSubscriber<MovieDetails> subscriber = TestSubscriber.create();
        if(null != mNetworkAPI) {
            mNetworkAPI.getMovieDetails("tt0057012").subscribe(subscriber);
            subscriber.assertNoErrors();
            subscriber.assertCompleted();
        }
    }
}
