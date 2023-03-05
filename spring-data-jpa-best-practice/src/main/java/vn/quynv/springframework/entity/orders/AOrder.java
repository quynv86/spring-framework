package vn.quynv.springframework.entity.orders;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="tbl_order")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="code")
    private String code;
    @Column(name="amount")
    private Double amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name="tbl_order_delivery",
        joinColumns = {
            @JoinColumn (name="order_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name="delivery_id")
        }
    )
    private DeliveryInfo item;
}
