package vn.quynv.springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.quynv.springframework.entity.orders.AOrder;
import vn.quynv.springframework.entity.orders.DeliveryInfo;
import vn.quynv.springframework.repository.DeliveryInfoRepository;
import vn.quynv.springframework.repository.OrderRepository;

@SpringBootTest
@Slf4j
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;

    @Test
    void createAnOrder() {
        DeliveryInfo deliveryInfo = DeliveryInfo.builder()
                .mobilePhone("092828282")
                .build();
        AOrder anOrder = AOrder.builder().amount(19.1)
                .code("IPHONE-14")
                .item(deliveryInfo)
                .build();
        deliveryInfoRepository.save(deliveryInfo);
        orderRepository.save(anOrder);
        // Query To Verify
        orderRepository.findAll().forEach((ord)-> {
            log.info("Order: {}, Delivery For: {}", ord.getCode(), ord.getItem().getMobilePhone());
        });

        orderRepository.delete(anOrder);
    }
}
