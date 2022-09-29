package vn.quynv.springframework.webflux.controller.temperature;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureResponse {
    private long gaugeId;
    private Date timestamp;
    private Double value;
}
