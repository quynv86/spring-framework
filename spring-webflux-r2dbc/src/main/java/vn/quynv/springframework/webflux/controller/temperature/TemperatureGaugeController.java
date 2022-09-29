package vn.quynv.springframework.webflux.controller.temperature;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Date;

@RestController
@RequestMapping("/temperatureGauge")
public class TemperatureGaugeController {
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TemperatureResponse> getTemperature() {
//        Flux<TemperatureResponse> response = Flux.range(1,10).map((number) -> TemperatureResponse.builder().gaugeId(number).timestamp(new Date()).value(number*1.0).build()).doOnNext((temp) -> {
//            try {
//                Thread.sleep(1_000);
//            }catch(Exception ex) {
//                ex.printStackTrace();
//            }
//        });
        return Flux.create((sink) -> {
            this.
        });
//        return response;
    }


}
