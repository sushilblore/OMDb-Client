package com.android.sushil.omdbclient.ui.main.MoviesList;

import com.android.sushil.omdbclient.data.model.SearchResults;
import com.android.sushil.omdbclient.ui.base.BasePresenter;
import com.android.sushil.omdbclient.ui.base.BaseView;

/**
 * Created by sushiljha on 09/09/2017.
 */

public class MoviesListContract {
    public interface View extends BaseView<Presenter> {
        void onMovieListLoadSuccess(SearchResults searchResultResponse);
        void onMovieListLoadFailure(Throwable t);
    }

    public interface Presenter extends BasePresenter {
        void loadMoviesListItems(String query, String type, int gaeNumber);
    }
}
