package com.android.sushil.omdbclient.data.remote;

import com.android.sushil.omdbclient.data.model.MovieDetails;
import com.android.sushil.omdbclient.data.model.SearchResults;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sushiljha on 09/09/2017.
 */

public interface NetworkAPI {

    @GET("/?apikey=475db1a5")
    Observable<SearchResults> searchMovies(@Query("s") String query, @Query("type") String type, @Query("page") int page);

    @GET("/?apikey=475db1a5")
    Observable<MovieDetails> getMovieDetails(@Query("i") String imdbId);
}
