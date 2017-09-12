package common;

import com.android.sushil.omdbclient.data.model.MovieDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {


    public static MovieDetails makeMovieDeatilsObject() {
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setTitle("Titanic");
        movieDetails.setCountry("USA");
        movieDetails.setActors("Leanordo");

        return movieDetails;
    }
}