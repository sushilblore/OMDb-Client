package com.android.sushil.omdbclient;

import android.util.Log;

import com.android.sushil.omdbclient.data.model.MovieDetails;
import com.android.sushil.omdbclient.data.remote.NetworkAPI;
import com.android.sushil.omdbclient.data.remote.NetworkService;
import com.android.sushil.omdbclient.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import common.TestDataFactory;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class NetworkServiceUnitTest {

    @Mock
    NetworkAPI mNetworkAPI;
    NetworkService mNetworkService;

    @Before
    public void setUp() throws Exception {
        mNetworkService = new NetworkService(mNetworkAPI);
    }

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();


    @Test
    public void testMovieDetailsEmptyResponse() throws Exception {
        String result;
        MovieDetails movieDetails = new MovieDetails();
        when(mNetworkAPI.getMovieDetails("tt0057012")).thenReturn(Observable.<MovieDetails>just(movieDetails));

        mNetworkService.getMovieDetails(new NetworkService.GetMovieDetailsCallback() {
            @Override
            public void onSuccess(MovieDetails movieDetailsResponse) {
                verify(movieDetailsResponse.getResponse().equals("true"));
            }

            @Override
            public void onError(Throwable e) {

            }

        },"tt0057012");
    }

    @Test
    public void testMovieDetailsErrorResponse() throws Exception {

        when(mNetworkAPI.getMovieDetails("tt0057012")).thenReturn(Observable.<MovieDetails>error(new RuntimeException()));
        mNetworkService.getMovieDetails(new NetworkService.GetMovieDetailsCallback() {
            @Override
            public void onSuccess(MovieDetails movieDetailsResponse) {
            }

            @Override
            public void onError(Throwable e) {
                verify(e.getMessage().equals("false"));

            }

        },"tt0057012");
    }

    @Test
    public void testMovieDetailsWithDataResponse() throws Exception {
        MovieDetails movieDetails = TestDataFactory.makeMovieDeatilsObject();
        when(mNetworkAPI.getMovieDetails("tt0057012")).thenReturn(Observable.<MovieDetails>just(movieDetails));
        mNetworkService.getMovieDetails(new NetworkService.GetMovieDetailsCallback() {
            @Override
            public void onSuccess(MovieDetails movieDetailsResponse) {
                verify(movieDetailsResponse.getTitle().equals("Titanic"));
            }

            @Override
            public void onError(Throwable e) {

            }

        },"tt0057012");
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.getInstance().reset();
    }
}