package vn.quynv.springframework.cloud.integration.httpinboud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.integration.jdbc.MessagePreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.messaging.MessageHandler;
import vn.quynv.springframework.domain.Movie;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
@Slf4j
public class MovieWebIntegrationConfiguration {
    @Autowired
    private MovieWebProperties movieWebProperties;

    private final String PUBLISHER_NAME = "publisher";

    @Bean
    public IntegrationFlow httpInboundFlow() {

        return IntegrationFlows.from(Http.inboundChannelAdapter(path())
            .requestMapping((requestMappingSpec)->{
                // Define HTTP Request Mapping such as Method, Consume Content-Type, Producer Type and so on.
                requestMappingSpec.methods(HttpMethod.POST);
            })
            .requestPayloadType(Movie.class)
        ).channel(MessageChannels.publishSubscribe(PUBLISHER_NAME))
        .get();

    }
    @Bean
    public IntegrationFlow logFlow() {
        return IntegrationFlows.from(PUBLISHER_NAME)
                .log(LoggingHandler.Level.INFO,"Movie",(msgMovie)->msgMovie)
                .get();
    }

    public String path() {
        log.info("Http Inbound Adapter HTTP - PATH: {}", this.movieWebProperties.getPath());
        return this.movieWebProperties.getPath();
    }


    @Bean
    @ServiceActivator(inputChannel = PUBLISHER_NAME)
    public MessageHandler process(DataSource dataSource) {
        final String INSERT_COMMAND = "insert into MOVIES (TITLE, ACTOR, YEAR ) values (?, ?, ?)";
        JdbcMessageHandler jdbcMessageHandler = new JdbcMessageHandler(dataSource,INSERT_COMMAND);
        jdbcMessageHandler.setPreparedStatementSetter(preparedStatementSetter());
        return jdbcMessageHandler;
    }

    private MessagePreparedStatementSetter preparedStatementSetter() {
        return (preparedStatement,message) -> {
            Movie movie = (Movie)message.getPayload();
            preparedStatement.setString(1,movie.getTitle());
            preparedStatement.setString(2,movie.getActor());
            preparedStatement.setInt(3,movie.getYear());
        };
    }
}
