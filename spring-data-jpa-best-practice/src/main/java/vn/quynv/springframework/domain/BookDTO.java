package vn.quynv.springframework.domain;

import lombok.*;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String isbn;
    private String title;
    private Long authorId;
}
