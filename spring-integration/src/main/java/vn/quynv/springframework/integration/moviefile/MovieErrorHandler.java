package vn.quynv.springframework.integration.moviefile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;

@MessageEndpoint
@Slf4j
public class MovieErrorHandler {
    @ServiceActivator
    public void onMovieFileError(MessageHandlingException exception, @Headers Map<String, Object> headers) {

        headers.entrySet().stream().forEach((entrySet)->{
            log.info("Key: {} -- Value: {}",entrySet.getKey(), entrySet.getValue());
        });
        log.info("Error File. {}", exception.getRootCause().getMessage());

    }
}
