package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.orders.DeliveryInfo;

@Repository
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
}
