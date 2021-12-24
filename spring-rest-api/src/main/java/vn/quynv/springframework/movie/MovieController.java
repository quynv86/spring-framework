package vn.quynv.springframework.movie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.quynv.springframework.domain.Movie;

import java.util.List;

@RestController
@Slf4j
public class MovieController {

    @PostMapping(value="/v1/movie", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> movie(@RequestBody Movie movie) {
        log.info("Movie received : {}", movie.toString());
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }

    @PostMapping(value="/v1/movies", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> movies(@RequestBody List<Movie> movies) {
        movies.stream().forEach((movie) -> {
            log.info(movie.toString());
        });
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }
}
