package vn.quynv.springframework.integration.moviehttp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import vn.quynv.springframework.domain.Movie;

import java.util.List;
import java.util.Map;

@MessageEndpoint
@Slf4j
public class MovieHttpHandler {

    @ServiceActivator
    public void handleSingleMovie(Movie movie, @Headers Map<String,Object> headers) {
        log.info("Movie Received: {}", movie);
    }

    @ServiceActivator
    public void handleMultipleMovie(List<Movie> movies, @Headers Map<String,Object> headers) {
        movies.stream().forEach( movie -> {
                    log.info("Movie Received: {}", movie);
                });
    }
}
