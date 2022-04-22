package vn.quynv.springframework.service;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import vn.quynv.springframework.repository.PublisherRepository;
import vn.quynv.springframework.service.specification.BookSpecifications;

import java.util.List;

@Service
@Slf4j
public class BookStoreService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookReviewRepository bookReviewRepository;
    private PublisherRepository publisherRepository;

    public BookStoreService(BookRepository bookRepository, AuthorRepository authorRepository, BookReviewRepository bookReviewRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookReviewRepository = bookReviewRepository;
        this.publisherRepository = publisherRepository;
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

    @Transactional(readOnly = true)
    public List<Book> findAllUsingLeftJoinFetch() {
        return bookRepository.findAllUsingLeftJoinFetch();
    }

    @Transactional(readOnly = true)
    public Page<Book> findAll_Using_Specification(String isbn, String title, PageRequest pageRequest) {
        return bookRepository.findAll(BookSpecifications.hasIsbnLike(isbn)
                .and(BookSpecifications.hasTitle(title))
        , pageRequest);
    }

    public List<Book> queryBySpecification(BookSpecifications.BookQuery query) {
        return bookRepository.findAll(BookSpecifications.bySpecific(query));
    }
    public List<Book> findBooksUsingEntityManager() {
        return bookRepository.findBookUsingEntityManager();
    }
}
