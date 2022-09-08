package vn.quynv.springframework.entity.inheritance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="animal")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@Setter
@Getter
public class Animal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
}
