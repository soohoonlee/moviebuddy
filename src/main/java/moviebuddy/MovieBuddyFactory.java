package moviebuddy;

import moviebuddy.data.CsvMovieReader;
import moviebuddy.data.XmlMovieReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@ComponentScan(basePackages = { "moviebuddy" })
@Import({ MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class })
public class MovieBuddyFactory {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("moviebuddy");

        return marshaller;
    }

    @Configuration
    static class DomainModuleConfig {

    }

    @Configuration
    static class DataSourceModuleConfig {

        @Profile(MovieBuddyProfile.CSV_MODE)
        @Bean
        public CsvMovieReader csvMovieReader() {
            final CsvMovieReader movieReader = new CsvMovieReader();
            movieReader.setMetadata("movie_metadata.csv");

            return movieReader;
        }

        @Profile(MovieBuddyProfile.XML_MODE)
        @Bean
        public XmlMovieReader xmlMovieReader(final Unmarshaller unmarshaller) {
            final XmlMovieReader movieReader = new XmlMovieReader(unmarshaller);
            movieReader.setMetadata("movie_metadata.xml");

            return movieReader;
        }
    }
}
