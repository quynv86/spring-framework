package vn.quynv.springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import vn.quynv.springframework.domain.ReviewStatus;
import vn.quynv.springframework.entity.BookReview;
import vn.quynv.springframework.event.CheckReviewEvent;
import vn.quynv.springframework.repository.BookReviewRepository;

@Service
@Slf4j
public class CheckReviewEventHandler {

    private final BookReviewRepository bookReviewRepository;

    @Autowired
    public CheckReviewEventHandler(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
//    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    /* Can phan biet ro truoc khi commit va sau khi commit. --. Refer : 247 Topic: Publish Event
    *  Nen can phai chu y: TRANSACTIONAL REQUIRE_NEW for its working
    * */
    public void handleCheckReviewEvent(CheckReviewEvent event) {
        log.info("Handling event: [Id: {}, Content: {}]", event.getBookReview().getId(),event.getBookReview().getContent());
        safeSleep(2);
        BookReview bookReview = bookReviewRepository.findById(event.getBookReview().getId()).get();
        log.info("Loaded Book Review. Current status is: {}", bookReview.getStatus().toString());
        bookReview.setStatus(ReviewStatus.APPROVE);
        throw new RuntimeException("What happened...");
    }

    private void safeSleep(long seconds) {
        try {
            Thread.sleep(1000 * seconds);
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
