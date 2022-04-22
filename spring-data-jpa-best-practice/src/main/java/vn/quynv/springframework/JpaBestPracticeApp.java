package vn.quynv.springframework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.quynv.springframework.domain.BookDTO;
import vn.quynv.springframework.domain.BookReviewDTO;
import vn.quynv.springframework.domain.ReviewStatus;
import vn.quynv.springframework.entity.Book;
import vn.quynv.springframework.service.BookStoreService;

import java.util.Random;

@SpringBootApplication
@Slf4j
public class JpaBestPracticeApp {
    public static void main(String[] args) {
        SpringApplication.run(JpaBestPracticeApp.class, args);
    }

    @Autowired
    private BookStoreService bookStoreService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("Do nothing here, i prefer using unit test to execute all repositories and services in this project.");
        };
    }
    static final void printLog(Book book) {
        log.info("ISBN: {}, Author: {}, Publisher: {}", book.getIsbn(), book.getAuthor().getName(), book.getAuthor().getPublisher().getCompany());
    }



}
