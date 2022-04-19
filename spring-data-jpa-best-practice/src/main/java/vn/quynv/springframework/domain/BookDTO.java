package vn.quynv.springframework.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class BookDTO {
    private Long id;
    private String isbn;
    private String title;
    private Long authorId;
}
