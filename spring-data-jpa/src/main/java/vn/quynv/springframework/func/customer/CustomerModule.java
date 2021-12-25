package vn.quynv.springframework.func.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.quynv.springframework.domain.customer.Customer;
import vn.quynv.springframework.domain.customer.CustomerDTO;
import vn.quynv.springframework.domain.customer.CustomerStatus;
import vn.quynv.springframework.domain.customer.CustomerType;
import vn.quynv.springframework.repository.customer.CustomerRes;
import vn.quynv.springframework.repository.customer.CustomerTypeRes;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class CustomerModule {
    @Autowired
    CustomerTypeRes customerTypeRes;

    @Autowired
    CustomerRes customerRes;

    @Bean
    public Function<String, Optional<CustomerType>> queryCustomerTypeByType() {
        return (type) -> {
            return customerTypeRes.queryCustomerTypeByCustTypeEquals(type);
        };
    }

    @Bean
    public Function<CustomerStatus, Collection<Customer>> queryCustomerByStatus() {
        return (customerStatus -> {
           return customerRes.queryCustomerByStatusIs(customerStatus);
        });
    }

    @Bean
    public Function <Customer, CustomerDTO> mapCustomerToDTO() {
        return (customer) -> {
            if(customer ==null) return null;
            return CustomerDTO.builder().name(customer.getName()).build();
        };
    }

    @Bean
    public Function <Collection<Customer>, Collection<CustomerDTO>> mapListCustomerToListDTO() {
        return (customer) -> {
            return customer.stream().map(mapCustomerToDTO())
                    .filter(dto-> dto!=null)
                    .collect(Collectors.toList());
        };
    }
    @Bean
    public Function<Collection<CustomerDTO>, Collection<CustomerDTO>> upperCaseByList() {
        return customerDTOs -> {
           return customerDTOs.stream().map(dto ->CustomerDTO.builder().name(dto.getName().toUpperCase()).build()
           ).collect(Collectors.toList());
        };
    }

    @Bean
    public  Function queryCustomerByCustTypeIn () {
        return (listCustType) -> {
            return customerRes.queryCustomerByCustTypeIn((List<String>)listCustType);
        };
    }

    @Bean
    public Function<Object,String> writeAsJson() {
        return (obj) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                return "{}";
            }
        };
    }

}
