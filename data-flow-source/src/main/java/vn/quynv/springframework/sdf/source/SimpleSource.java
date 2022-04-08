package vn.quynv.springframework.sdf.source;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.messaging.handler.annotation.SendTo;
import vn.quynv.springframework.sdf.domain.AppMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class SimpleSource {
    @Bean
    Supplier<AppMessage> publishEvent() {
        return () -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          if(new Random().nextBoolean()) {
              return AppMessage.builder().channel("CHAN").message("Create Time is: " + simpleDateFormat.format(new Date())).build();
          }
          return AppMessage.builder().channel("LE").message("Create Time is: " + simpleDateFormat.format(new Date())).build();
        };
    }
}
