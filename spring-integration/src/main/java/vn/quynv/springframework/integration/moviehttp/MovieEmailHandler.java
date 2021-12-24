package vn.quynv.springframework.integration.moviehttp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import vn.quynv.springframework.domain.Movie;

import java.util.Map;

@MessageEndpoint
@Slf4j
public class MovieEmailHandler {
    @ServiceActivator
    public void sendEmail(@Payload  Movie movie , @Headers  Map<String,Object> headers) {
        log.info("Sending and email for use about new movie: {}", movie);
    }
}
