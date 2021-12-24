package vn.quynv.springframework.integration.moviefile;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.quynv.springframework.domain.Movie;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieService {

    @Autowired
    ObjectMapper objectMapper;

    public String format(String contents) {
        String json = "{}";
        log.info("Formatting to Json ...");
        try {
            json = objectMapper.writeValueAsString(parse(contents));
        }catch(IOException e) {
            log.warn("An error entered when parsing content to json. Return empty json string instated", e);
        }
        return json;
     }

    private List<Movie> parse(String contents) {
        return Arrays.asList(contents.split(System.getProperty("line.separator")))
                .stream().map(line -> {
                    String fields[] = line.split(",");

                    return Movie.builder().title(fields[0])
                            .actor(fields[1])
                            .year(Integer.parseInt(fields[2]))
                            .build();
        }).collect(Collectors.toList());
    }
}
