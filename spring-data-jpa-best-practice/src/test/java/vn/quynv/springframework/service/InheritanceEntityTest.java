package vn.quynv.springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.quynv.springframework.entity.inheritance.Animal;
import vn.quynv.springframework.entity.inheritance.Cat;
import vn.quynv.springframework.entity.inheritance.Dog;
import vn.quynv.springframework.repository.AnimalRepository;
import vn.quynv.springframework.repository.CatRepository;
import vn.quynv.springframework.repository.DogRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
public class InheritanceEntityTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private DogRepository dogRepository;


    @Autowired
    private AnimalRepository animalRepository;


    @BeforeEach
    @Transactional
    void prepareData() {
        Cat cat = new Cat();
        cat.setName("Tom");
        cat.setFurColor("WHITE");
        animalRepository.save(cat);

        Dog dog = new Dog();
        dog.setOrigin("VIETNAM");
        dog.setName("Bull");
        animalRepository.save(dog);
    }

    @Test
    void queryAllAnimal() {
        entityManager.createQuery("select a from Animal a", Animal.class)
                .getResultList().forEach(a -> {
                    log.info("Got Animal with name: {}. Type: {}", a.getName(), a.getClass().getName());

                });

    }
}
