package vn.quynv.springframework.domain.customer;

import java.util.stream.Stream;

public enum CustomerStatus {

    ACTIVE(1), INACTIVE(0);
    private int value;

    private CustomerStatus(int value) {
        this.value = value;
    }
    public int getValue () {
        return value;
    }
    public static CustomerStatus of(int value) {
        return Stream.of(CustomerStatus.values())
                .filter(customerStatus -> value== customerStatus.getValue())
                .findFirst().orElse(INACTIVE);
    }
}
