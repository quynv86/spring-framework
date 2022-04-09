package vn.quynv.springframework.sdf.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.messaging.handler.annotation.SendTo;
import vn.quynv.springframework.sdf.conf.AppProperties;
import vn.quynv.springframework.sdf.domain.AppMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@Slf4j
@EnableConfigurationProperties(AppProperties.class)
public class SimpleProcessor {

    final private AppProperties appProperties;

    @Autowired
    public SimpleProcessor(AppProperties appProperties) {
        this.appProperties = appProperties;
    }
    @Bean
    Function<AppMessage, AppMessage> processAppMessage() {
        return (appMessage) -> {
            if(appMessage.getChannel().equals("LE")) {
                log.info("Skip message with channel LE");
                return null;
            }
            appMessage.setMessage(appMessage.getMessage() + " . Processed at: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
          return appMessage;
        };
    }
}
