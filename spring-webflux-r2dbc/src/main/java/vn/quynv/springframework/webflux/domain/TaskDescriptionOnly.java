package vn.quynv.springframework.webflux.domain;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@Builder
public class TaskDescriptionOnly {
    private String description;
}
