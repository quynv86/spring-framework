package vn.quynv.springframework.domain.customer;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerDTO {
    private String name;
}
