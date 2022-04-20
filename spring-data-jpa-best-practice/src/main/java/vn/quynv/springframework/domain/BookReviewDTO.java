package vn.quynv.springframework.domain;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookReviewDTO {
    private Long id;
    private String content;
    private String email;
    private ReviewStatus status;
    private Long bookId;
}
