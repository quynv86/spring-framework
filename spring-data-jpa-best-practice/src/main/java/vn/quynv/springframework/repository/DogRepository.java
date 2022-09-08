package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.inheritance.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog,Long> {
}
