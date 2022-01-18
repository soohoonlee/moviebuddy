package moviebuddy.domain;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import moviebuddy.ApplicationException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class JaxbMovieReader implements MovieReader {

    @Override
    public List<Movie> loadMovies() {
        try {
            final JAXBContext jaxb = JAXBContext.newInstance(MovieMetadata.class);
            final Unmarshaller unmarshaller = jaxb.createUnmarshaller();

            final InputStream content = ClassLoader.getSystemResourceAsStream("movie_metadata.xml");
            final Source source = new StreamSource(content);
            final MovieMetadata metadata = (MovieMetadata) unmarshaller.unmarshal(source);

            return metadata.toMovies();
        } catch (JAXBException error) {
            throw new ApplicationException("failed to load movies data", error);
        }
    }

    @XmlRootElement(name = "moviemetadata")
    public static class MovieMetadata {

        private List<MovieData> movies;

        public List<MovieData> getMovies() {
            return movies;
        }

        public void setMovies(List<MovieData> movies) {
            this.movies = movies;
        }

        public List<Movie> toMovies() {
            return movies.stream()
                    .map(MovieData::toMovie)
                    .collect(Collectors.toList());
        }
    }

    public static class MovieData {

        private String title;
        private List<String> genres;
        private String language;
        private String country;
        private int releaseYear;
        private String director;
        private List<String> actors;
        private URL imdbLink;
        private String watchedDate;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getReleaseYear() {
            return releaseYear;
        }

        public void setReleaseYear(int releaseYear) {
            this.releaseYear = releaseYear;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public List<String> getActors() {
            return actors;
        }

        public void setActors(List<String> actors) {
            this.actors = actors;
        }

        public URL getImdbLink() {
            return imdbLink;
        }

        public void setImdbLink(URL imdbLink) {
            this.imdbLink = imdbLink;
        }

        public String getWatchedDate() {
            return watchedDate;
        }

        public void setWatchedDate(String watchedDate) {
            this.watchedDate = watchedDate;
        }

        public Movie toMovie() {
            final String title = getTitle();
            final List<String> genres = getGenres();
            final String language = getLanguage();
            final String country = getCountry();
            final int releaseYear = getReleaseYear();
            final String director = getDirector();
            final List<String> actors = getActors();
            final URL imdbLink = getImdbLink();
            final String watchedDate = getWatchedDate();

            return Movie.of(title, genres, language, country, releaseYear, director, actors, imdbLink, watchedDate);
        }
    }
}