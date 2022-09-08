package vn.quynv.springframework.entity.inheritance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@DiscriminatorValue("CAT")
public class Cat extends Animal{
    @Column(name="fur_color")
    String furColor;
}
