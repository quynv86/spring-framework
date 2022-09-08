package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.inheritance.Cat;

@Repository
public interface CatRepository extends JpaRepository<Cat,Long> {
}
