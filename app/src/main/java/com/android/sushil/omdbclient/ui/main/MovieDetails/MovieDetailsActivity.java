package com.android.sushil.omdbclient.ui.main.MovieDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sushil.omdbclient.BaseApplication;
import com.android.sushil.omdbclient.R;
import com.android.sushil.omdbclient.data.model.MovieDetails;
import com.android.sushil.omdbclient.data.remote.NetworkService;
import com.android.sushil.omdbclient.utils.ActivityUtils;
import com.android.sushil.omdbclient.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View {

    private String mImdbId;
    private String mImageUrl;
    private MovieDetailsPresenter mPresenter;

    private ImageView mPosterImage;
    private TextView mMovieName;
    private TextView mMovieYear;
    private TextView mMovieRated;
    private TextView mMovieReleased;
    private TextView mMovieRuntime;
    private TextView mMovieGenre;
    private TextView mMovieDirector;
    private TextView mMovieWriter;
    private TextView mMovieActors;
    private TextView mMovieLanguage;
    private TextView mMovieCountry;
    private TextView mMovieIMDBRating;
    private TextView mMoviePlot;

    @Inject
    public NetworkService mNetworkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle dataBundle = getIntent().getExtras();
        if (dataBundle != null) {
            mImdbId = dataBundle.getString(Constants.ARG_ID);
            mImageUrl = dataBundle.getString(Constants.ARG_URL);
        }

        ((BaseApplication)getApplication()).getDependency().inject(this);
        setPresenter(new MovieDetailsPresenter(mNetworkService, this));

        mPosterImage = (ImageView) findViewById(R.id.img_poster);
        mMovieName = (TextView) findViewById(R.id.movie_name);
        mMovieYear = (TextView) findViewById(R.id.movie_year);
        mMovieRated = (TextView) findViewById(R.id.movie_rated);
        mMovieReleased = (TextView) findViewById(R.id.movie_released);
        mMovieRuntime = (TextView) findViewById(R.id.movie_runtime);
        mMovieGenre = (TextView) findViewById(R.id.movie_genre);
        mMovieDirector = (TextView) findViewById(R.id.movie_director);
        mMovieWriter = (TextView) findViewById(R.id.movie_writer);
        mMovieActors = (TextView) findViewById(R.id.movie_actors);
        mMovieLanguage = (TextView) findViewById(R.id.movie_language);
        mMovieCountry = (TextView) findViewById(R.id.movie_country);
        mMovieIMDBRating = (TextView) findViewById(R.id.movie_imdb_rating);
        mMoviePlot = (TextView) findViewById(R.id.movie_plot);


        Glide.with(this)
                .load(mImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .error(R.drawable.image_not_available)
                .into(mPosterImage);

        mPresenter.loadMovieDetails(mImdbId);
    }

    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        mPresenter = (MovieDetailsPresenter)presenter;
    }

    @Override
    public void onMovieDetailsLoadSuccess(MovieDetails movieDetailsResponse) {

        mMovieName.setText(getResources().getString(R.string.name,  movieDetailsResponse.getTitle()));
        mMovieYear.setText(getResources().getString(R.string.year, movieDetailsResponse.getYear()));
        mMovieRated.setText(getResources().getString(R.string.rated, movieDetailsResponse.getRated()));
        mMovieReleased.setText(getResources().getString(R.string.released, movieDetailsResponse.getReleased()));
        mMovieRuntime.setText(getResources().getString(R.string.runtime, movieDetailsResponse.getRuntime()));
        mMovieGenre.setText(getResources().getString(R.string.genre, movieDetailsResponse.getGenre()));
        mMovieDirector.setText(getResources().getString(R.string.director, movieDetailsResponse.getDirector()));
        mMovieWriter.setText(getResources().getString(R.string.writer, movieDetailsResponse.getWriter()));
        mMovieActors.setText(getResources().getString(R.string.actors, movieDetailsResponse.getActors()));
        mMovieLanguage.setText(getResources().getString(R.string.language, movieDetailsResponse.getLanguage()));
        mMovieCountry.setText(getResources().getString(R.string.country, movieDetailsResponse.getCountry()));
        mMovieIMDBRating.setText(getResources().getString(R.string.imdb_ratng, movieDetailsResponse.getImdbRating()));
        mMoviePlot.setText(getResources().getString(R.string.plot, movieDetailsResponse.getPlot()));
    }

    @Override
    public void onMovieDetailsLoadFailure(Throwable t) {
        String errorText = ActivityUtils.fetchErrorMessage(t, this);
        Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorView(Throwable t) {

    }

    @Override
    public void hideErrorView() {

    }
}
