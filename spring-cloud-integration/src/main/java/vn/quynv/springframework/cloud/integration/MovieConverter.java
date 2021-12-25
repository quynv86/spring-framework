package vn.quynv.springframework.cloud.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import vn.quynv.springframework.domain.Movie;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class MovieConverter implements Converter<String, Movie> {
    @Override
    public Movie convert(String source) {
        if(source==null) return null;
        log.info("Converting string: {} to Movie object...", source);
        List<String> fields = Stream.of(source.split(","))
                .map(String::trim).collect(Collectors.toList());
        return Movie.builder().title(fields.get(0))
                .actor(fields.get(1))
                .year(Integer.parseInt(fields.get(2)))
                .build();
    }
}
