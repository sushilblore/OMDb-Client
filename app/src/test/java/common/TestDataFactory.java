package common;

import com.android.sushil.omdbclient.data.model.MovieDetails;

public class TestDataFactory {


    public static MovieDetails makeMovieDeatilsObject() {
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setTitle("Titanic");
        movieDetails.setCountry("USA");
        movieDetails.setActors("Leonardo");

        return movieDetails;
    }
}