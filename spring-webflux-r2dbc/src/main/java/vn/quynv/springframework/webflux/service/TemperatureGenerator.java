package vn.quynv.springframework.webflux.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import vn.quynv.springframework.webflux.controller.temperature.TemperatureResponse;

import java.util.function.Consumer;

@Slf4j
@Component
public class TemperatureGenerator {
    private Consumer<TemperatureResponse> consumer;

    public Flux<TemperatureResponse> getFluxTemperature() {
     return Flux.create(fluxSink -> {
         consumer = item -> fluxSink.next(item);
        });
    }

    @Scheduled(fixedDelay = 5_000)
    public void getTemperature() {
//        consumer.accept(TemperatureResponse.builder().gaugeId(number).timestamp(new Date()).value(number*1.0).build());
    }
}
