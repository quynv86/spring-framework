package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
