package vn.quynv.springframework.cloud.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.file.dsl.FileInboundChannelAdapterSpec;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.splitter.FileSplitter;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.dsl.HttpMessageHandlerSpec;

import java.io.File;
import java.net.URI;
//import org.springframework.integration

@Configuration
@EnableConfigurationProperties(MovieProperties.class)
@AllArgsConstructor
@Slf4j
public class MovieIntegrationConfiguration {
    private MovieProperties movieProperties;
    private MovieConverter movieConverter;
    /**
     * SPLIT ==> This method creates a splitter that sets the payload as Iterable, Iterator, Array, Stream,
     * or a reactive Publisher, and it accepts an implementation of the splitter;
     * in this case, we are using Files.splitter.markers(),
     * which splits the file contents into lines and marks the start and end of the file.
     *
     * FILTER ==> This method filters the lines we are sending.
     * It’s important to have a filter because the first and last line of the file’s content is a marker (START - END)
     */

    @Bean
    public IntegrationFlow fileFlow() {
        return IntegrationFlows.from(
                fileInboundAdapter()
                , e -> e.poller(Pollers.fixedDelay(fixedDelay()))
        ).split(Files.splitter().markers())
         .filter(p -> !(p instanceof FileSplitter.FileMarker))
         .transform(Transformers.converter(this.movieConverter))
         .transform(Transformers.toJson())
         .handle(httpOutboundAdapter())
         .get();
    }

    private HttpMessageHandlerSpec httpOutboundAdapter() {
        log.info("Http Outbound URI: {}", this.movieProperties.getRemoteService());
        return Http.outboundChannelAdapter(URI.create(this.movieProperties.getRemoteService()))
                .httpMethod(HttpMethod.POST);

    }

    private FileInboundChannelAdapterSpec fileInboundAdapter() {
        return Files.inboundAdapter(directory())
                .preventDuplicates(preventDuplicates())
                .patternFilter(patternFilter());
    }

    private long fixedDelay() {
        log.info("File Inbound Adapter Fixed-Delay: {}",this.movieProperties.getFixedDelay());
        return this.movieProperties.getFixedDelay();
    }

    private String patternFilter() {
        log.info("File Inbound Adapter File Pattern: {}",this.movieProperties.getFilePattern());
        return this.movieProperties.getFilePattern();
    }

    private File directory() {
        log.info("File Inbound Adapter Directory: {}",this.movieProperties.getDirectory());
        return new File(this.movieProperties.getDirectory());
    }
    private Boolean preventDuplicates() {
        log.info("File Inbound Adapter Prevent-Duplicates: {}",movieProperties.getPreventDuplicates());
        return this.movieProperties.getPreventDuplicates();
    }
}
