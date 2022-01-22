package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import moviebuddy.MovieBuddyProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

/**
 * @author springrunner.kr@gmail.com
 */
@ActiveProfiles(MovieBuddyProfile.CSV_MODE)
@SpringJUnitConfig(MovieBuddyFactory.class)
class MovieFinderTest {

    @Autowired MovieFinder movieFinder;

    @Autowired ApplicationContext applicationContext;

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

    @Test
    void Equals_MovieFinderBean() {
        Assertions.assertEquals(movieFinder, applicationContext.getBean(MovieFinder.class));
    }
}
