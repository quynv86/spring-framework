package vn.quynv.springframework.entity.orders;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="tbl_delivery")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DeliveryInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="mobile_phone")
    private String mobilePhone;
}
