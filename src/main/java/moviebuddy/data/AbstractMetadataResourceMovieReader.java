package moviebuddy.data;

import moviebuddy.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileNotFoundException;

public abstract class AbstractMetadataResourceMovieReader implements ResourceLoaderAware {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private String metadata;
    private ResourceLoader resourceLoader;

    public String getMetadata() {
        return metadata;
    }

    @Value("${movie.metadata}")
    public void setMetadata(final String metadata) {
        this.metadata = metadata;
    }

    public Resource getMetadataResource() {
        return resourceLoader.getResource(getMetadata());
    }

    @Override
    public void setResourceLoader(final ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void afterPropertiesSet() throws FileNotFoundException {
        final Resource resource = getMetadataResource();
        if (!resource.exists()) {
            throw new FileNotFoundException(metadata);
        }
        if (!resource.isReadable()) {
            throw new ApplicationException(String.format("cannot read to metadata. [%s]", metadata));
        }
        log.info("{} is ready.", resource);
    }

    @PreDestroy
    public void destroy() {
        log.info("Destroyed bean");
    }
}
