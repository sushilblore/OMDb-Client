package common;

import com.android.sushil.omdbclient.data.model.MovieDetails;
import com.android.sushil.omdbclient.data.model.Search;
import com.android.sushil.omdbclient.data.model.SearchResults;

import java.util.ArrayList;
import java.util.List;

public class TestDataFactory {


    public static MovieDetails makeMovieDeatilsObject() {
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setTitle("Titanic");
        movieDetails.setCountry("USA");
        movieDetails.setActors("Leonardo");

        return movieDetails;
    }

    public static SearchResults makeSearchResultsObject() {
        int resultSize = 5;
        SearchResults searchResults = new SearchResults();
        searchResults.setTotalResults(String.valueOf(resultSize));
        searchResults.setSearch(makeResultMoviesList(resultSize));
        return searchResults;
    }

    public static List<Search> makeResultMoviesList(int number) {
        List<Search> users = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            users.add(makeSearchItem(String.valueOf(i)));
        }
        return users;
    }

    public static Search makeSearchItem(String uniqueSuffix) {
        Search s = new Search();
        s.setTitle("Live" + uniqueSuffix);
        s.setType("movie");
        s.setYear(uniqueSuffix);
        return s;
    }
}