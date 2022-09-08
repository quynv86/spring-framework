package vn.quynv.springframework.entity.inheritance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@DiscriminatorValue("DOG")
public class Dog extends Animal{
    @Column(name="origin")
    String origin;
}
