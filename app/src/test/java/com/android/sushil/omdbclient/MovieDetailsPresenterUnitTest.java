package com.android.sushil.omdbclient;

import com.android.sushil.omdbclient.data.model.MovieDetails;
import com.android.sushil.omdbclient.data.remote.NetworkAPI;
import com.android.sushil.omdbclient.data.remote.NetworkService;
import com.android.sushil.omdbclient.ui.main.MovieDetails.MovieDetailsActivity;
import com.android.sushil.omdbclient.ui.main.MovieDetails.MovieDetailsContract;
import com.android.sushil.omdbclient.ui.main.MovieDetails.MovieDetailsPresenter;
import com.android.sushil.omdbclient.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import rx.Subscription;
import rx.android.plugins.RxAndroidPlugins;
import rx.subscriptions.CompositeSubscription;

import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsPresenterUnitTest {

    @Mock
    NetworkAPI mNetworkAPI;

    @Mock
    NetworkService mNetworkService;

    @Mock
    MovieDetailsContract.View mView;

    @Mock
    MovieDetailsActivity mMovieDetailsActivity;

    private MovieDetailsPresenter mMovieDetailsPresenter;


    @Before
    public void setUp() throws Exception {
        this.mMovieDetailsPresenter = new MovieDetailsPresenter(mNetworkService, mMovieDetailsActivity);
    }

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();


    @Test
    public void testMovieDetailsEmptyResponse() throws Exception {
        MovieDetails movieDetails = new MovieDetails();
        when(mNetworkAPI.getMovieDetails("abcd")).thenReturn(Observable.<MovieDetails>just(movieDetails));
        mMovieDetailsPresenter.loadMovieDetails("abcd");
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.getInstance().reset();
    }
}