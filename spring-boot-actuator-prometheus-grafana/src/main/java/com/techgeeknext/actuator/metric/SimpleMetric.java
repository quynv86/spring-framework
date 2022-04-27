package com.techgeeknext.actuator.metric;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
//@EnableScheduling
public class SimpleMetric {
    private final MeterRegistry meterRegistry;


    @Autowired
    public SimpleMetric(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        exposeMetric();
    }

//    @Scheduled(fixedRateString = "3000", initialDelayString = "0")
    public void exposeMetric() {
            Gauge.builder("spring-boot-gauge", () -> {return new Random().nextInt(100);
        }).tag("tag-key","get-and-decrypt").register(meterRegistry);

        Gauge.builder("spring-boot-gauge", () -> {return new Random().nextInt(100);
        }).tag("tag-key","import-file").register(meterRegistry);

        Gauge.builder("spring-boot-gauge", () -> {return new Random().nextInt(100);
        }).tag("tag-key","calculate-fee").register(meterRegistry);

        log.info("Try to expose new Gauge");
    }

}
