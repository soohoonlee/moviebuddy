package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author springrunner.kr@gmail.com
 */
class MovieFinderTest {

    final MovieBuddyFactory movieBuddyFactory = new MovieBuddyFactory();
    final MovieFinder movieFinder = movieBuddyFactory.movieFinder();

    @Test
    void NotEmpty_directedBy() {
        final List<Movie> movies = movieFinder.directedBy("Michael Bay");
        Assertions.assertEquals(3, movies.size());
    }

    @Test
    void NotEmpty_ReleasedYearBy() {
        final List<Movie> movies = movieFinder.releasedYearBy(2015);
        Assertions.assertEquals(225, movies.size());
    }
}
