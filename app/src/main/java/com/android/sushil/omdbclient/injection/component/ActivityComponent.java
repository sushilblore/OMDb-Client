package com.android.sushil.omdbclient.injection.component;

import com.android.sushil.omdbclient.injection.module.NetworkModule;
import com.android.sushil.omdbclient.ui.main.MovieDetails.MovieDetailsActivity;
import com.android.sushil.omdbclient.ui.main.MoviesList.MoviesListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sushiljha on 09/09/2017.
 */

@Singleton
@Component(modules = {NetworkModule.class,})
public interface ActivityComponent {
    void inject(MoviesListActivity moviesListActivity);
    void inject(MovieDetailsActivity movieDetailsActivity);
}
