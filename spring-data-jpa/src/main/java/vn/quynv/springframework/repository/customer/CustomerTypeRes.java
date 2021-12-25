package vn.quynv.springframework.repository.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.quynv.springframework.domain.customer.CustomerType;

import java.util.Optional;

public interface CustomerTypeRes extends JpaRepository<CustomerType, Long> {
    Optional<CustomerType> queryCustomerTypeByCustTypeEquals(String custType);
}
