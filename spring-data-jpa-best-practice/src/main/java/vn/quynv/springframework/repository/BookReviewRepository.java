package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.BookReview;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
}
