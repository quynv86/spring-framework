package vn.quynv.springframework.integration.moviefile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@Slf4j
@MessageEndpoint
public class MovieHandler {

    @Autowired
    private MovieService movieService;

    @ServiceActivator
    public void process(File fileInput, @Headers Map<String, Object> headers) throws Exception{
        log.info("Recived file: {}", fileInput.getName());
        FileInputStream fileInputStream = new FileInputStream(fileInput);
        String moviesAsJsonString = movieService.format(new String(StreamUtils.copyToByteArray(fileInputStream)));
        fileInputStream.close();
        log.info("Movies received: \n {}", moviesAsJsonString);
    }
}
