package vn.quynv.springframework.domain.customer;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    /*Remember That : Default Fetch Type : EAGER*/
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="cust_type", referencedColumnName = "id")
    private CustomerType customerType;
    @Column
    private CustomerStatus status;

}
