package vn.quynv.springframework.domain.client;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    private String shortName;
    private String name;
}
