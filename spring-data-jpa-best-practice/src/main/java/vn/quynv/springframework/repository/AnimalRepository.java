package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.inheritance.Animal;


@Repository
public interface AnimalRepository extends JpaRepository<Animal,Long> {
}
