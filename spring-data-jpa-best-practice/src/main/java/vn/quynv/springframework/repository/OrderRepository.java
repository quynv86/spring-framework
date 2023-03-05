package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.orders.AOrder;

@Repository
public interface OrderRepository extends JpaRepository<AOrder, Long> {
}
