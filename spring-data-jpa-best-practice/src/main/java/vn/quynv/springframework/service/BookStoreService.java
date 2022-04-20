package vn.quynv.springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vn.quynv.springframework.domain.BookDTO;
import vn.quynv.springframework.domain.BookReviewDTO;
import vn.quynv.springframework.entity.Author;
import vn.quynv.springframework.entity.Book;
import vn.quynv.springframework.entity.BookReview;
import vn.quynv.springframework.repository.AuthorRepository;
import vn.quynv.springframework.repository.BookRepository;
import vn.quynv.springframework.repository.BookReviewRepository;

@Service
@Slf4j
public class BookStoreService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookReviewRepository bookReviewRepository;

    public BookStoreService(BookRepository bookRepository, AuthorRepository authorRepository
            , BookReviewRepository bookReviewRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookReviewRepository = bookReviewRepository;
    }

    @Autowired


    @Transactional(readOnly = true)
    public void loadAllAuthorAndPrintThem() {
        // Default load: using lazy loading
//        authorRepository.findAll().forEach(author -> log.info("{}",author));
    }
    @Transactional
    public void addBook(BookDTO bookDTO) {
        log.info("Add new BOOK {}", bookDTO);
        // Load using EnityGrap -> Load Eager
        Author author = authorRepository.findAuthorById(bookDTO.getAuthorId());
        Book book = new Book();
        book.setAuthor(author);
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        author.getBooks().add(book);
        Book firstBook = author.getBooks().get(0);
        author.getBooks().remove(firstBook);
    }

    @Transactional
    public void postReview(BookReviewDTO bookReviewDTO) {
        Book book = bookRepository.getById(bookReviewDTO.getBookId());
        Assert.notNull(book,String.format("Not found book with id %d", bookReviewDTO.getBookId()));
        BookReview bookReview = new BookReview();
        bookReview.setBook(book);
        bookReview.setContent(bookReviewDTO.getContent());
        bookReview.setEmail(bookReviewDTO.getEmail());
        bookReview.setStatus(bookReviewDTO.getStatus());
        bookReview.registerEvent();
        bookReviewRepository.save(bookReview);
    }
}
