package vn.quynv.springframework.entity;

import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import vn.quynv.springframework.domain.ReviewStatus;
import vn.quynv.springframework.event.CheckReviewEvent;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookReview extends AbstractAggregateRoot<BookReview> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String email;
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public void registerEvent() {
        super.registerEvent(new CheckReviewEvent(this));
    }
}
