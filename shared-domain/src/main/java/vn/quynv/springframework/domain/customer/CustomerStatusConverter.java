package vn.quynv.springframework.domain.customer;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomerStatusConverter implements AttributeConverter<CustomerStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CustomerStatus customerStatus) {
        return customerStatus.getValue();
    }
    @Override
    public CustomerStatus convertToEntityAttribute(Integer integer) {
        return CustomerStatus.of(integer);
    }
}
