package com.android.sushil.omdbclient.ui.main.MovieDetails;

import com.android.sushil.omdbclient.data.model.MovieDetails;
import com.android.sushil.omdbclient.ui.base.BasePresenter;
import com.android.sushil.omdbclient.ui.base.BaseView;

/**
 * Created by sushiljha on 10/09/2017.
 */

public class MovieDetailsContract {
    public interface View extends BaseView<Presenter> {
        void onMovieDetailsLoadSuccess(MovieDetails movieDetailsResponse);
        void onMovieDetailsLoadFailure(Throwable t);
        void showErrorView(Throwable t);
        void hideErrorView();
    }

    public interface Presenter extends BasePresenter {
        void loadMovieDetails(String id);
    }
}
