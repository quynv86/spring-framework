package vn.quynv.springframework.jpa;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import vn.quynv.springframework.domain.customer.Customer;
import vn.quynv.springframework.domain.customer.CustomerDTO;
import vn.quynv.springframework.domain.customer.CustomerStatus;
import vn.quynv.springframework.domain.customer.CustomerType;
import vn.quynv.springframework.func.customer.CustomerFucFactory;
import vn.quynv.springframework.repository.customer.CustomerRes;
import vn.quynv.springframework.repository.customer.CustomerTypeRes;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class CustomerResTest {

    @Autowired
    private CustomerRes customerRes;

    @Autowired
    private CustomerTypeRes customerTypeRes;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CustomerFucFactory fucFactory;

    @BeforeEach
    private void prepareCustomerData() {
        IntStream.range(1,10).boxed()
                .map((number)->{
                    return Customer.builder()
                            .name("CustName" + number)
                            .customerType(getOrCreateIfNotExists(number))
                            .status(CustomerStatus.of(number%2))
                            .build();

                }).forEach(customerRes::save);
    }

    private CustomerType getOrCreateIfNotExists(Integer number) {
        final CustomerType.CustomerTypeBuilder builder =  CustomerType.builder();
        if(number<3) {
            builder.custType("VVIP");
        }else if(number<6) {
            builder.custType("VIP");
        }else {
            builder.custType("NORMAL");
        }
        CustomerType typeDto = builder.build();
        return customerTypeRes.queryCustomerTypeByCustTypeEquals(typeDto.getCustType())
                .orElseGet(() -> {
                    log.info(" =============> CREATE NEW CUSTOMER TYPE {}", typeDto.getCustType());
                    return customerTypeRes.save(builder.build());

                });
    }

    @Test
    @Transactional
    void when_Select_All_Customer() {
        Long count = customerRes.findAll().stream().count();
        log.info("========>Total customer: {}", count);
        Assertions.assertEquals(count,9);
    }

    @Test
    @Transactional
    void when_Query_Name_End_With_1() {
        Assertions.assertEquals(customerRes.queryCustomerByNameEndingWith("1").stream().count()
                ,1);
        customerRes.queryCustomerByNameEndingWith("1")
                .stream()
                .forEach((customer)-> {
                    log.info("======> {}", customer);
                });
    }

    @Test
    @Transactional
    void when_Query_Active_Customer() {
        var query = applicationContext.getBean("queryCustomerByStatus",Function.class);
        var transform = applicationContext.getBean("mapListCustomerToListDTO",Function.class);
        var upercase = applicationContext.getBean("upperCaseByList",Function.class);

        Assertions.assertEquals(customerRes.queryActivatedCustomer()
                .stream().count()
                ,customerRes.queryCustomerByStatusIs(CustomerStatus.ACTIVE)
                        .stream().count());


        var composite = query.andThen(transform).andThen(upercase);
        ((Collection<CustomerDTO>)composite.apply(CustomerStatus.ACTIVE)).forEach((customer)-> {
            log.info("===> CUSTOMER_DTO : {}", customer);
        });

    }

    @Test
    @Transactional
    public void query_Customer_Type_Cust_Types() {
//        customerRes.queryCustomerByCustTypeIn(Arrays.asList("VIP")).stream()
//                .forEach(customer -> {
//                    log.info("=============> CUSTOMER: {}", customer);
//                });
        var query = applicationContext.getBean("queryCustomerByCustTypeIn",Function.class);
        var transform = applicationContext.getBean("mapListCustomerToListDTO",Function.class);
        var upercase = applicationContext.getBean("upperCaseByList",Function.class);
        var composite = query.andThen(transform).andThen(upercase);
        ((Collection<CustomerDTO>)composite.apply(Arrays.asList("VIP")))
                .forEach((customer)-> {
            log.info("===> CUSTOMER_DTO : {}", customer);
        });
        log.info("---------------------------------------------------------------------------");
        String pipeLine = "queryCustomerByCustTypeIn,mapListCustomerToListDTO,upperCaseByList,writeAsJson";
        var otherComposite = fucFactory.generateFunction(pipeLine);
        log.info(otherComposite.apply(Arrays.asList("VIP","NORMAL")).toString());
    }

}
