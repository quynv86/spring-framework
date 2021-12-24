package vn.quynv.springframework.domain;

import lombok.*;

import java.io.Serializable;

@Data
@Builder @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movie implements Serializable {
    private String title;
    private String actor;
    private int year;
}
