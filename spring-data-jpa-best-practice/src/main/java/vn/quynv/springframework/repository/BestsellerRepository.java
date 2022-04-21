package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.Bestseller;

@Repository
public interface BestsellerRepository extends JpaRepository<Bestseller, Long> {
}
