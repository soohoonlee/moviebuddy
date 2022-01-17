package moviebuddy.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class JaxbMovieReaderTest {

    @Test
    void NotEmpty_LoadedMovies() {
        final JaxbMovieReader movieReader = new JaxbMovieReader();

        final List<Movie> movies = movieReader.loadMovies();
        Assertions.assertEquals(1375, movies.size());
    }
}
